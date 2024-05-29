package com.lebastudios.sealcode.frontend;

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
