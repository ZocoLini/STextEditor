package com.lebastudios.stexteditor.iobjects.nodes;

import com.lebastudios.stexteditor.events.AppEvent;
import javafx.geometry.VPos;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.*;
import org.fxmisc.richtext.model.StyledDocument;

import java.util.Collection;

public class FormateableText extends CodeArea
{
    public static class OnTextInsertion extends AppEvent {}
    public OnTextInsertion onTextInsertion = new OnTextInsertion();

    public FormateableText(String string)
    {
        super(string);

        this.addDefaultEventHandlers();
        
        this.setParagraphGraphicFactory(LineNumberFactory.get(this));
    }


    private void addDefaultEventHandlers()
    {

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

    @Override
    public void replace(int start, int end, StyledDocument<Collection<String>, String, Collection<String>> replacement)
    {
        if (onTextInsertion != null)
        {
            onTextInsertion.invoke();
        }

        super.replace(start, end, replacement);
    }
}
