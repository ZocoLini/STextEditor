package com.lebastudios.stexteditor.exceptions;

import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.NotificationsContainerManager;
import com.lebastudios.stexteditor.iobjects.nodes.Notification;

public class IdeVersionMethodNotImplemented extends RuntimeException
{
    public IdeVersionMethodNotImplemented()
    {
        super("This method is not implemented in this version of the IDE.");

        NotificationsContainerManager.getInstance().
                addNotification(new Notification("This function is not implemented in this version of the IDE."));
    }
}
