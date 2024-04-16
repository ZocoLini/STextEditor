package com.lebastudios.sealcode.iobjects.fxextends.buttons;

import com.lebastudios.sealcode.exceptions.IdeVersionMethodNotImplemented;
import javafx.event.ActionEvent;

public class CompileButton extends ButtonManager
{
    @Override
    protected String iconID()
    {
        return "compile.png";
    }

    @Override
    public void onAction(ActionEvent event)
    {
        throw new IdeVersionMethodNotImplemented();
    }
}
