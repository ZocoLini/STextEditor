package com.lebastudios.sealcode.frontend.fxextends;

import com.lebastudios.sealcode.applogic.events.AppEvent;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyledDocument;

import java.util.Collection;

public final class FormateableText extends CodeArea
{
    public final OnTextInsertion onTextInsertion = new OnTextInsertion();

    public FormateableText(String string)
    {
        super(string);

        this.addEventHandlers();

        this.setParagraphGraphicFactory(LineNumberFactory.get(this));
    }

    private void addEventHandlers()
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

    public static class OnTextInsertion extends AppEvent {}
}