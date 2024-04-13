package com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.leftvbox;

import com.lebastudios.stexteditor.applogic.config.global.Session;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.ButtonManager;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.MainManager;
import javafx.event.ActionEvent;
import javafx.scene.control.SplitPane;

import java.io.File;

public class NotificationsButtonManager extends ButtonManager
{
    private static NotificationsButtonManager instance;

    public static NotificationsButtonManager getInstance()
    {
        if (instance == null) instance = new NotificationsButtonManager();

        return instance;
    }

    private NotificationsButtonManager()
    {
        super(MainManager.getInstance().botonNotificaciones);
    }

    @Override
    protected String iconID()
    {
        return "notificaciones.png";
    }

    @Override
    public void onAction(ActionEvent event)
    {
        final var notifications = MainManager.getInstance().notifications;
        SplitPane codeTabPaneContainer = MainManager.getInstance().codeTabPaneContainer;
        final var items = codeTabPaneContainer.getItems();

        if (!items.remove(notifications))
        {
            items.addLast(notifications);
            codeTabPaneContainer.setDividerPosition(items.indexOf(notifications), 0.80);
        }
    }
}
