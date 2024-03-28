package com.lebastudios.stexteditor.nodes.formateableText;

import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.*;

public class FormatteableText extends CodeArea
{
    public FormatteableText(String string) {
        super();
        this.replaceText(0, 0, string);
        
        this.addDefaultEventHandlers();
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
