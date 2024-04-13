package com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.leftvbox;

import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.ButtonManager;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.MainManager;
import javafx.event.ActionEvent;

public class FileSystemButtonManager extends ButtonManager
{
    private static FileSystemButtonManager instance;
    
    public static FileSystemButtonManager getInstance()
    {
        if (instance == null) instance = new FileSystemButtonManager();
        
        return instance;
    }
    
    public FileSystemButtonManager()
    {
        super(MainManager.getInstance().fileSystemButtonManager);
    }

    @Override
    protected String iconID()
    {
        return "fileSystem.png";
    }

    @Override
    public void onAction(ActionEvent event)
    {
        LeftVBoxManager.getInstance().alternarVisibilidadNode();
    }
}
