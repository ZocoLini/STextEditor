package com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.rightvbox;

import com.lebastudios.stexteditor.applogic.Resources;
import com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.MainSingletonManager;
import com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.SingletonManager;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class ExecuteButtonSingletonManager extends SingletonManager<Button>
{
    private static ExecuteButtonSingletonManager instance;

    public static ExecuteButtonSingletonManager getInstance()
    {
        if (instance == null) instance = new ExecuteButtonSingletonManager();

        return instance;
    }

    private ExecuteButtonSingletonManager()
    {
        super(MainSingletonManager.getInstance().botonEjecutar);

        instanciated = true;
    }

    @Override
    protected void loadShelf()
    {
        ImageView compileIcon = new ImageView(Resources.getIcon("play.jpg"));
        representingObject.setGraphic(compileIcon);
    }
    
    @Override
    public void loadChilds()
    {
        
    }
}
