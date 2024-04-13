package com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers;

import com.lebastudios.stexteditor.applogic.Resources;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public abstract class ButtonManager extends SingletonManager<Button>
{
    public ButtonManager(Button managedObject)
    {
        super(managedObject);

        ImageView compileIcon = new ImageView(Resources.getIcon(iconID()));
        compileIcon.setFitHeight(20);
        compileIcon.setFitWidth(20);
        this.managedObject.setGraphic(compileIcon);

        managedObject.setOnAction(this::onAction);
    }

    protected abstract String iconID();

    public abstract void onAction(ActionEvent event);

    @Override
    protected final void loadChilds() {}
}
