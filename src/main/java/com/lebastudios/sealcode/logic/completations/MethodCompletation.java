package com.lebastudios.sealcode.logic.completations;

import com.lebastudios.sealcode.util.Completation;

public class MethodCompletation extends Completation
{
    public MethodCompletation(String value)
    {
        super(value, "method.png");
    }

    @Override
    public String getDescription()
    {
        return "Method";
    }

    @Override
    public String getCompletation()
    {
        return getValue() + "()";
    }
}
