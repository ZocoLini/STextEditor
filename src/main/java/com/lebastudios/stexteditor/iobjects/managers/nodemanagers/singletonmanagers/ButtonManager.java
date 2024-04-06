package com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers;

import com.lebastudios.stexteditor.applogic.Resources;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public abstract class ButtonManager extends SingletonManager<Button>
{
    public ButtonManager(Button managedObject)
    {
        super(managedObject);

        ImageView compileIcon = new ImageView(Resources.getIcon(iconPath()));
        compileIcon.setFitHeight(30);
        compileIcon.setFitWidth(30);
        this.managedObject.setGraphic(compileIcon);
    }

    protected abstract String iconPath();
    
    public abstract void onAction();
    
    @Override
    protected void loadChilds()
    {
        
    }
}
