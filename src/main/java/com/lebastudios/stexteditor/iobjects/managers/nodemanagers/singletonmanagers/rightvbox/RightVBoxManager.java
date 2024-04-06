package com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.rightvbox;

import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.MainManager;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.SingletonManager;
import javafx.scene.layout.VBox;

public class RightVBoxManager extends SingletonManager<VBox>
{
    private static RightVBoxManager instance;
    
    public static RightVBoxManager getInstance()
    {
        if (instance == null) 
        {
            instance = new RightVBoxManager();
        }
        
        return instance;
    }
    
    private RightVBoxManager()
    {
        super(MainManager.getInstance().rightVBox);

    }

    @Override
    public void loadChilds()
    {
        CompileButtonManager.getInstance().load();
        ExecuteButtonManager.getInstance().load();
        TerminalButtonManager.getInstance().load();
    }
}
