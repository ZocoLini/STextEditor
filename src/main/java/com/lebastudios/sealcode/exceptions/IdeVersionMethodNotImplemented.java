package com.lebastudios.sealcode.exceptions;

import com.lebastudios.sealcode.frontend.fxextends.Notification;
import com.lebastudios.sealcode.frontend.stages.main.MainStageController;

public class IdeVersionMethodNotImplemented extends RuntimeException
{
    public IdeVersionMethodNotImplemented()
    {
        super("This method is not implemented in this version of the IDE.");

        MainStageController.addNotification(new Notification("This function is not implemented in this version of " +
                "the IDE."));
    }
}
