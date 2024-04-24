package com.lebastudios.sealcode.core.frontend.fxextends;

import javafx.scene.control.TextField;

public class IntegerField extends TextField
{
    public IntegerField(int initialNumber)
    {
        super(String.valueOf(initialNumber));
    }
    
    public IntegerField()
    {
        this(0);
    }
}
