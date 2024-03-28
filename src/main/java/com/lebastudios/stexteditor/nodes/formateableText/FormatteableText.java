package com.lebastudios.stexteditor.nodes.formateableText;

import com.lebastudios.stexteditor.app.config.Theme;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import org.fxmisc.richtext.*;

public class FormatteableText extends CodeArea
{
    public FormatteableText(String string) {
        super(string);
        
        this.addDefaultEventHandlers();
        
        this.setLineHighlighterOn(true);
        this.setParagraphGraphicFactory(LineNumberFactory.get(this));
        this.setLineHighlighterFill(Color.valueOf(Theme.getStaticInstance().colorLine));
    }


    private void addDefaultEventHandlers() {
        
        // Añade un evento en el que, si se pusa Ctrl + Z, se deshace la última acción
        this.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() 
                    && event.getCode().equals(javafx.scene.input.KeyCode.Z)
                    && !event.isShiftDown()
                    && this.isUndoAvailable()) {
                this.undo();
            }
        });

        // Añade un evento en el que, si se pusa Ctrl + Y, se rehace la última acción
        this.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() 
                    && event.getCode().equals(javafx.scene.input.KeyCode.Y)
                    && !event.isShiftDown()
                    && this.isRedoAvailable()) {
                this.redo();
            }
        });
    }
}
