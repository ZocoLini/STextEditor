package com.lebastudios.stexteditor.nodes.formateableText;

import com.lebastudios.stexteditor.events.TextInsertionListener;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.*;
import org.fxmisc.richtext.model.StyledDocument;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FormateableText extends CodeArea
{
    private final List<TextInsertionListener> insertionListeners = new ArrayList<>();

    public FormateableText(String string)
    {
        super(string);

        this.addDefaultEventHandlers();

        this.setLineHighlighterOn(true);
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

    public void addTextInsertionListener(TextInsertionListener listener)
    {
        insertionListeners.add(listener);
    }

    public void removeTextInsertionListener(TextInsertionListener listener)
    {
        insertionListeners.remove(listener);
    }

    @Override
    public void replace(int start, int end, StyledDocument<Collection<String>, String, Collection<String>> replacement)
    {
        // notify all listeners
        if (insertionListeners != null)
        {
            for (TextInsertionListener listener : insertionListeners)
            {
                listener.codeInserted(start, end, replacement.getText());
            }
        }

        super.replace(start, end, replacement);
    }
}
