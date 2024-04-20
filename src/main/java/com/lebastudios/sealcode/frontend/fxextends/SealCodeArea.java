package com.lebastudios.sealcode.frontend.fxextends;

import com.lebastudios.sealcode.applogic.completations.Autocompletations;
import com.lebastudios.sealcode.applogic.FileOperation;
import com.lebastudios.sealcode.applogic.Resources;
import com.lebastudios.sealcode.applogic.config.GlobalConfig;
import com.lebastudios.sealcode.applogic.txtformatter.BracketHighlighter;
import com.lebastudios.sealcode.applogic.txtformatter.KeyWordHighlighter;
import com.lebastudios.sealcode.events.AppEvents;
import com.lebastudios.sealcode.frontend.fxextends.treeviews.FileSystemTreeItem;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public final class SealCodeArea extends CodeArea
{
    private final FileSystemTreeItem fileSystemTreeItem;
    public final String fileExtension;
    
    private boolean modified = false;
    
    public SealCodeArea(String string, FileSystemTreeItem fileSystemTreeItem)
    {
        super(string);

        fileExtension = FileOperation.getFileExtension(fileSystemTreeItem.getRepresentingFile());
        
        this.fileSystemTreeItem = fileSystemTreeItem;
        
        this.addEventHandlers();

        this.setParagraphGraphicFactory(LineNumberFactory.get(this));
        
        updateResources();
        
        new BracketHighlighter(this);
        new KeyWordHighlighter(this, fileExtension);
        new Autocompletations(this);
    }

    private void updateResources()
    {
        final String fileExtension = FileOperation.getFileExtension(fileSystemTreeItem.getRepresentingFile());
        
        this.getStylesheets().add(Resources.getLangCommonStyle());
        this.getStylesheets().add(Resources.getExtensionStyle(fileExtension));
    }
    
    private void addEventHandlers()
    {
        // Cambia el estilo de resaltado al cambaiar de tema
        AppEvents.onThemeChange.addListener(this::updateResources);
        
        // Añade un evento en el que, si se pusa Ctrl + Z, se deshace la última acción
        this.addEventHandler(KeyEvent.KEY_PRESSED, event ->
        {
            if (event.isControlDown()
                    && event.getCode().equals(javafx.scene.input.KeyCode.Z)
                    && !event.isShiftDown()
                    && this.isUndoAvailable())
            {
                this.undo();
            }
        });

        // Añade un evento en el que, si se pusa Ctrl + Y, se rehace la última acción
        this.addEventHandler(KeyEvent.KEY_PRESSED, event ->
        {
            if (event.isControlDown()
                    && event.getCode().equals(javafx.scene.input.KeyCode.Y)
                    && !event.isShiftDown()
                    && this.isRedoAvailable())
            {
                this.redo();
            }
        });
    }

    public void saveFile()
    {
        if (!this.modified) return;
        
        new Thread(() ->
        {
            try (FileWriter fileWriter = new FileWriter(fileSystemTreeItem.getRepresentingFile()))
            {
                fileWriter.write(this.getText());
                System.out.println("Archivo guardado:" + fileSystemTreeItem.getRepresentingFile());
                modified = false;
            }
            catch (IOException exception)
            {
                System.err.println("Error al guardar el archivo: " + exception.getMessage());
            }
        }).start();
    }
    
    // TODO: Se esta haciendo la configuración para lenguajes del tipo C. Buscar manera de hacerlo dependiendo del lenguaje
    @Override
    public void replace(int start, int end, StyledDocument<Collection<String>, String, Collection<String>> replacement)
    {
        modified = true;

        final String newText = replacement.getText();
        final String oldText = this.getText(start, end);
        
        // Remplaza los tabs por espacios según los ajustes del editor
        if (newText.equals("\t")) 
        {
            super.replace(start, end, " ".repeat(GlobalConfig.getStaticInstance().editorConfig.tabSize), "");
            return;
        }
        
        // Cuando se hace enter, indentamos si es necesario
        if (newText.equals("\n")) 
        {
            int indentacionLineStart;

            final var firstParagraphText = this.getParagraph(this.getCurrentParagraph()).getText();
            for (indentacionLineStart = 0; indentacionLineStart < firstParagraphText.length(); indentacionLineStart++) 
            {
                if (firstParagraphText.charAt(indentacionLineStart) != ' ') 
                {
                    break;
                }
            }
            
            super.replace(start, end, "\n" + " ".repeat(indentacionLineStart), "");
            this.moveTo(end + indentacionLineStart + 1);
            return;
        }
        
        // Cuando se inserta un caracter del tipo parentesis, corchete, llave, comillas o comillas simples, se añade el par
        Map<String, String> completingPair = Map.of(
                "(", ")",
                "{", "}",
                "[", "]",
                "\"", "\"",
                "'", "'"
        );
        
        if (completingPair.containsKey(newText)) 
        {
            super.replace(start, end, newText + completingPair.get(newText), "");
            this.moveTo(start + 1);
            return;
        }
        
        // Cuando se borra un caracter del tipo parentesis, corchete, llave, comillas o comillas simples, se borra el par
        if (completingPair.containsKey(oldText) && newText.isEmpty()  && start + 1 < this.getText().length()
                && this.getText(start + 1, start + 2).equals(completingPair.get(oldText)))
        {
            super.replace(start, end + 1, "", "");
            return;
        }

        try
        {
            if (Objects.equals(completingPair.get(this.getText(start - 1, end)), newText))
            {
                super.replace(start, end, "", "");
                this.moveTo(end + 1);
                return;
            }
        }
        catch (Exception ignored) {}
        
        super.replace(start, end, replacement);
    }
}
