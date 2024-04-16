package com.lebastudios.sealcode.exceptions;

import com.lebastudios.sealcode.iobjects.managers.nodemanagers.singletonmanagers.NotificationsContainerManager;
import com.lebastudios.sealcode.iobjects.fxextends.Notification;

public class IdeVersionMethodNotImplemented extends RuntimeException
{
  public IdeVersionMethodNotImplemented()
  {
    super("This method is not implemented in this version of the IDE.");

    NotificationsContainerManager.getInstance().
            addNotification(new Notification("This function is not implemented in this version of the IDE."));
  }
}
