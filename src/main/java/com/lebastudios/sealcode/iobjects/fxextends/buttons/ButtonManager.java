package com.lebastudios.sealcode.iobjects.fxextends.buttons;

import com.lebastudios.sealcode.applogic.Resources;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public abstract class ButtonManager extends Button
{
    public ButtonManager()
    {
        super();

        ImageView compileIcon = new ImageView(Resources.getIcon(iconID()));
        compileIcon.setFitHeight(20);
        compileIcon.setFitWidth(20);
        this.setGraphic(compileIcon);

        this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(0), Insets.EMPTY)));

        this.setOnAction(this::onAction);
    }

    protected abstract String iconID();

    public abstract void onAction(ActionEvent event);
}
