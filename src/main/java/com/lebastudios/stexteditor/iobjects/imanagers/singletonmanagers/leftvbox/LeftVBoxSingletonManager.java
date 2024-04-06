package com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.leftvbox;

import com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.MainSingletonManager;
import com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.SingletonManager;
import javafx.scene.layout.VBox;

public class LeftVBoxSingletonManager extends SingletonManager<VBox>
{
    private static LeftVBoxSingletonManager instance;
    
    public static LeftVBoxSingletonManager getInstance()
    {
        if (instance == null) 
        {
            instance = new LeftVBoxSingletonManager();
        }
        
        return instance;
    }
    
    private LeftVBoxSingletonManager()
    {
        super(MainSingletonManager.getInstance().leftVBox);
        
        instanciated = true;
    }

    @Override
    public void loadChilds()
    {
        
    }
}
