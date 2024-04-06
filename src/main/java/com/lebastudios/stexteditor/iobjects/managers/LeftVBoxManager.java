package com.lebastudios.stexteditor.iobjects.managers;

import javafx.scene.layout.VBox;

public class LeftVBoxManager extends Manager<VBox>
{
    private static LeftVBoxManager instance;
    
    public static LeftVBoxManager getInstance()
    {
        if (instance == null) 
        {
            instance = new LeftVBoxManager();
        }
        
        return instance;
    }
    
    private LeftVBoxManager()
    {
        super(MainManager.getInstance().leftVBox);
        
        instanciated = true;
    }
}
