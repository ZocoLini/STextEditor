package com.lebastudios.sealcode.frontend.fxextends;

import com.lebastudios.sealcode.applogic.FileOperation;
import com.lebastudios.sealcode.applogic.Resources;
import com.lebastudios.sealcode.applogic.txtformatter.BracketHighlighter;
import com.lebastudios.sealcode.applogic.txtformatter.KeyWordHighlighter;
import com.lebastudios.sealcode.events.AppEvent;
import com.lebastudios.sealcode.events.AppEvents;
import com.lebastudios.sealcode.frontend.fxextends.treeviews.FileSystemTreeItem;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyledDocument;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public final class SealCodeArea extends CodeArea
{
    public final OnTextInsertion onTextInsertion = new OnTextInsertion();

    private final FileSystemTreeItem fileSystemTreeItem;
    
    private boolean modified = false;
    
    public SealCodeArea(String string, FileSystemTreeItem fileSystemTreeItem)
    {
        super(string);

        this.fileSystemTreeItem = fileSystemTreeItem;
        
        this.addEventHandlers();

        this.setParagraphGraphicFactory(LineNumberFactory.get(this));
        
        updateResources();
    }

    private void updateResources()
    {
        this.getStylesheets().add(Resources.getLangCommonStyle());

        String fileExtension = FileOperation.getFileExtension(fileSystemTreeItem.getRepresentingFile());
        
        this.getStylesheets().add(Resources.getExtensionStyle(fileExtension));

        new BracketHighlighter(this);
        new KeyWordHighlighter(this, fileExtension);
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
    
    @Override
    public void replace(int start, int end, StyledDocument<Collection<String>, String, Collection<String>> replacement)
    {
        modified = true;
        
        if (onTextInsertion != null)
        {
            onTextInsertion.invoke();
        }

        super.replace(start, end, replacement);
    }

    public static class OnTextInsertion extends AppEvent {}
}
