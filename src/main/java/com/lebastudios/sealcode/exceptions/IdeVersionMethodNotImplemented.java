package com.lebastudios.sealcode.exceptions;

import com.lebastudios.sealcode.iobjects.fxextends.Notification;
import com.lebastudios.sealcode.iobjects.stages.main.MainStageController;

public class IdeVersionMethodNotImplemented extends RuntimeException
{
  public IdeVersionMethodNotImplemented()
  {
    super("This method is not implemented in this version of the IDE.");

    MainStageController.getInstance().notificationsContainer.addNotification(new Notification("This function is not " +
            "implemented in this version of " +
            "the IDE."));
  }
}
