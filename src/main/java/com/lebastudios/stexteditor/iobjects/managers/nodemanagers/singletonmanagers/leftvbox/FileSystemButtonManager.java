package com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.leftvbox;

import com.lebastudios.stexteditor.applogic.config.global.Session;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.ButtonManager;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.MainManager;
import javafx.event.ActionEvent;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

import java.io.File;

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
        final var proyectFileTreeView = MainManager.getInstance().proyectFileTreeView;
        SplitPane proyectTreeViewContainer = MainManager.getInstance().proyectTreeViewContainer;

        if (!proyectTreeViewContainer.getItems().remove(proyectFileTreeView))
        {
            proyectTreeViewContainer.getItems().addFirst(proyectFileTreeView);
            proyectTreeViewContainer.setDividerPositions(
                    0.01 * new File(Session.getStaticInstance().proyectDirectory).getName().length()
            );
        }
    }
}
