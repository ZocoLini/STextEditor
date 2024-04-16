package com.lebastudios.sealcode.iobjects.fxextends.buttons;

import com.lebastudios.sealcode.applogic.config.Session;
import com.lebastudios.sealcode.iobjects.stages.main.MainStageController;
import com.lebastudios.sealcode.iobjects.fxextends.Notification;
import javafx.event.ActionEvent;
import javafx.scene.control.SplitPane;

import java.io.File;

public class FileSystemButton extends ButtonManager
{
    @Override
    protected String iconID()
    {
        return "fileSystem.png";
    }

    @Override
    public void onAction(ActionEvent event)
    {
        MainStageController.getInstance().notificationsContainer.addNotification(new Notification("File System Button Pressed"));

        final var proyectFileTreeView = MainStageController.getInstance().fileSystemTreeView;
        SplitPane proyectTreeViewContainer = MainStageController.getInstance().codeTabPaneContainer;
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
