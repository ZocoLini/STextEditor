package com.lebastudios.stexteditor.nodes.formateableText;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class FormatteableText extends VBox
{

    private EditableTextLine[] shownTextLines = new EditableTextLine[0];

    public FormatteableText(String string) {
        super();
        String[] lines = string.split("\n");

        shownTextLines = new EditableTextLine[lines.length];

        for (int i = 0; i < lines.length; i++) {
            shownTextLines[i] = new EditableTextLine(new Text(lines[i]));
        }
        
        getChildren().addAll(shownTextLines);
    }
}
