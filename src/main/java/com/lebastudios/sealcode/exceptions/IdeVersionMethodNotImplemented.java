package com.lebastudios.sealcode.exceptions;

import com.lebastudios.sealcode.controllers.MainStageController;
import com.lebastudios.sealcode.frontend.fxextends.Notification;

public class IdeVersionMethodNotImplemented extends RuntimeException
{
    public IdeVersionMethodNotImplemented()
    {
        super("This method is not implemented in this version of the IDE.");

        MainStageController.getInstance().notificationsContainer.addNotification(new Notification("This function is not implemented in this version of " +
                "the IDE."));
    }
}
