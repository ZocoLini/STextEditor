package com.lebastudios.sealcode.frontend.fxextends.buttons;

import com.lebastudios.sealcode.applogic.Resources;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public abstract class Button extends javafx.scene.control.Button
{
    private static final int ICON_SIZE = 20;

    public Button()
    {
        super();

        ImageView compileIcon = new ImageView(Resources.getIcon(iconID()));
        compileIcon.setFitHeight(ICON_SIZE);
        compileIcon.setFitWidth(ICON_SIZE);
        this.setGraphic(compileIcon);

        this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(0), Insets.EMPTY)));
    }

    protected abstract String iconID();
}
