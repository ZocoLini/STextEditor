package com.lebastudios.sealcode.frontend.fxextends.buttons;

import com.lebastudios.sealcode.frontend.fxextends.IconView;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public abstract class Button extends javafx.scene.control.Button
{
    public Button()
    {
        super();

        IconView compileIcon = new IconView(iconID());
        
        this.setGraphic(compileIcon);

        this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(0), Insets.EMPTY)));
    }

    protected abstract String iconID();
}
