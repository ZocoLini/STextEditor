package com.lebastudios.sealcode.iobjects.managers.nodemanagers.singletonmanagers;

import com.lebastudios.sealcode.applogic.Resources;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public abstract class ButtonManager extends SingletonManager<Button>
{
    public ButtonManager(Button managedObject)
    {
        super(managedObject);

        ImageView compileIcon = new ImageView(Resources.getIcon(iconID()));
        compileIcon.setFitHeight(20);
        compileIcon.setFitWidth(20);
        this.managedObject.setGraphic(compileIcon);

        managedObject.setBackground(
                new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(0), Insets.EMPTY)));

        managedObject.setOnAction(this::onAction);
    }

    protected abstract String iconID();

    public abstract void onAction(ActionEvent event);

    @Override
    protected final void loadChilds() {}
}
