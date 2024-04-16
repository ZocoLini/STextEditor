package com.lebastudios.sealcode.iobjects.fxextends.buttons;

import com.lebastudios.sealcode.exceptions.IdeVersionMethodNotImplemented;
import javafx.event.ActionEvent;

public class ExecuteButton extends ButtonManager
{
    @Override
    protected String iconID()
    {
        return "play.png";
    }

    @Override
    public void onAction(ActionEvent event)
    {
        throw new IdeVersionMethodNotImplemented();
    }
}
