package com.lebastudios.sealcode.exceptions;

import com.lebastudios.sealcode.core.controllers.MainStageController;
import com.lebastudios.sealcode.core.frontend.fxextends.Notification;

public class IdeVersionMethodNotImplemented extends RuntimeException
{
    public IdeVersionMethodNotImplemented()
    {
        super("This method is not implemented in this version of the IDE.");

        MainStageController.getInstance().addNotification(new Notification("This function is not implemented in this version of " +
                "the IDE."));
    }
}
