package com.lebastudios.sealcode.iobjects.managers.nodemanagers.singletonmanagers.leftvbox;

import com.lebastudios.sealcode.applogic.config.Session;
import com.lebastudios.sealcode.iobjects.managers.nodemanagers.singletonmanagers.ButtonManager;
import com.lebastudios.sealcode.iobjects.managers.nodemanagers.singletonmanagers.MainManager;
import com.lebastudios.sealcode.iobjects.managers.nodemanagers.singletonmanagers.NotificationsContainerManager;
import com.lebastudios.sealcode.iobjects.fxextends.Notification;
import javafx.event.ActionEvent;
import javafx.scene.control.SplitPane;

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
        super(MainManager.getInstance().botonProyectFileSystem);
    }

    @Override
    protected String iconID()
    {
        return "fileSystem.png";
    }

    @Override
    public void onAction(ActionEvent event)
    {
        NotificationsContainerManager.getInstance().addNotification(new Notification("File System Button Pressed"));

        final var proyectFileTreeView = MainManager.getInstance().proyectFileTreeView;
        SplitPane proyectTreeViewContainer = MainManager.getInstance().codeTabPaneContainer;
        final var items = proyectTreeViewContainer.getItems();

        if (!items.remove(proyectFileTreeView))
        {
            items.addFirst(proyectFileTreeView);
            proyectTreeViewContainer.setDividerPosition(
                    items.indexOf(proyectFileTreeView),
                    0.01 * new File(Session.getStaticInstance().proyectDirectory).getName().length()
            );
        }
    }
}