package com.lebastudios.sealcode.frontend.fxextends;

import com.lebastudios.sealcode.applogic.DocumentsOperations;
import com.lebastudios.sealcode.applogic.FileOperation;
import com.lebastudios.sealcode.applogic.Resources;
import com.lebastudios.sealcode.applogic.completations.CompletationsPopup;
import com.lebastudios.sealcode.applogic.config.GlobalConfig;
import com.lebastudios.sealcode.applogic.txtformatter.BracketHighlighter;
import com.lebastudios.sealcode.applogic.txtformatter.KeyWordHighlighter;
import com.lebastudios.sealcode.applogic.txtmod.TextModParen;
import com.lebastudios.sealcode.events.AppEvents;
import com.lebastudios.sealcode.events.Events.*;
import com.lebastudios.sealcode.frontend.fxextends.treeviews.FileSystemTreeItem;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyledDocument;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static com.lebastudios.sealcode.applogic.DocumentsOperations.createStyledDocument;

public final class SealCodeArea extends CodeArea
{
    public OnTextInserted onTextInserted = new OnTextInserted();
    public OnTextDeleted onTextRemoved = new OnTextDeleted();
    public OnTextReplaced onTextReplaced = new OnTextReplaced();
    
    private final FileSystemTreeItem fileSystemTreeItem;
    public final String fileExtension;

    private boolean modified = false;
    private boolean initialized = false;

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
        new CompletationsPopup(this);
        
        // Text Modifications
        new TextModParen(this);
        
        initialized = true;
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
            } catch (IOException exception)
            {
                System.err.println("Error al guardar el archivo: " + exception.getMessage());
            }
        }).start();
    }

    public String getPreviusChar(int position)
    {
        if (position == 0) return "";
        return this.getText(position - 1, position);
    }

    public String getNoBlankPreviusChar(int position)
    {
        if (position == 0) return "";

        String previusChar = this.getText(position - 1, position);

        if (previusChar.equals(" ")) return getNoBlankPreviusChar(position - 1);

        return previusChar;
    }

    public String getNextChar(int position)
    {
        if (position == this.getText().length() - 1) return "";
        return this.getText(position, position + 1);
    }

    public String getNoBlankNextChar(int position)
    {
        if (position >= this.getText().length() - 1) return "";

        String nextChar = this.getText(position, position + 1);

        if (nextChar.equals(" ")) return getNoBlankNextChar(position + 1);

        return nextChar;
    }

    public int getParagraphIndentation()
    {
        final var firstParagraphText = this.getParagraph(this.getCurrentParagraph()).getText();
        int i;

        for (i = 0; i < firstParagraphText.length(); i++)
        {
            if (firstParagraphText.charAt(i) != ' ') return i;
        }

        return i;
    }

    public int paragraphStart(int paragraph)
    {
        int start = 0;

        for (int i = 0; i < paragraph; i++)
        {
            start += this.getParagraph(i).length() + 1;
        }

        return start;
    }

    public int paragraphEnd(int paragraph)
    {
        return paragraphStart(paragraph) + this.getParagraph(paragraph).length();
    }
    
    // TODO: Se esta haciendo la configuración para lenguajes del tipo C. Buscar manera de hacerlo dependiendo del lenguaje
    @Override
    public void replace(int start, int end, StyledDocument<Collection<String>, String, Collection<String>> replacement)
    {
        modified = true;
        
        int newCaretPosition = start;

        final String newText = replacement.getText();
        final String oldText = this.getText(start, end);
        String modifiedText = newText;

        if (!newText.isEmpty() && !oldText.isEmpty())
        {
            // Invocar eventos de reemplazo
            for (var variable : onTextReplaced.getListeners())
            {
                modifiedText = variable.textModified(start, end, oldText, modifiedText);
            }
        }
        else if (newText.isEmpty())
        {
            // Invocar eventos de borrado
            for (var variable : onTextRemoved.getListeners())
            {
                modifiedText = variable.textModified(start, end, oldText, modifiedText);
            }
        }
        else if (oldText.isEmpty())
        {
            // Invocar eventos de inserción
            if (initialized)
            {
                for (var variable : onTextInserted.getListeners())
                {
                    modifiedText = variable.textModified(start, end, oldText, modifiedText);
                }
            }
        }
        
        // Cuando se borra y el paragrafo solo son espacios, se borra_todo el paragrafo
        if (newText.isEmpty() && oldText.equals(" ") && getCurrentParagraph() != 0)
        {
            int paragraphStart = paragraphStart(getCurrentParagraph());
            int paragraphEnd = paragraphEnd(getCurrentParagraph());

            int caretPosition = this.getCaretPosition();

            if (getText(paragraphStart, caretPosition).trim().isEmpty())
            {
                super.replace(paragraphStart - 1, paragraphEnd, 
                        DocumentsOperations.createStyledDocument(getText().substring(caretPosition, paragraphEnd).trim()));
                this.moveTo(paragraphStart - 1);
                return;
            }
        }

        // Remplaza los tabs por espacios según los ajustes del editor
        modifiedText = modifiedText
                .replace("\t", " ".repeat(GlobalConfig.getStaticInstance().editorConfig.tabSize));

        // Remplaza los \n por \n + " " * indentación necesaria
        int actualIndentation = this.getParagraphIndentation();
        int indentationNeeded = actualIndentation;

        if (getNoBlankPreviusChar(start).equals("{") && oldText.isEmpty())
        {
            indentationNeeded += GlobalConfig.getStaticInstance().editorConfig.indentation;
        }

        modifiedText = modifiedText.replace("\n", "\n" + " ".repeat(indentationNeeded));

        if (getNoBlankNextChar(end).equals("}") && getNoBlankPreviusChar(start).equals("{") && oldText.isEmpty())
        {
            modifiedText += "$END$\n" + " ".repeat(actualIndentation);
        }

        // Procesamos las marcas $END$ como la ubicacion donde debe ir el caret
        boolean needToMoveCaret = false;
        
        if (modifiedText.contains("$END$"))
        {
            int caretDesiredPosition = modifiedText.indexOf("$END$");
            modifiedText = modifiedText.replace("$END$", "");
            needToMoveCaret = true;
            newCaretPosition = caretDesiredPosition + start;
        }

        var newReplacement = createStyledDocument(modifiedText);

        super.replace(start, end, newReplacement);

        if (needToMoveCaret)
        {
            this.moveTo(newCaretPosition);
        }
    }
}
