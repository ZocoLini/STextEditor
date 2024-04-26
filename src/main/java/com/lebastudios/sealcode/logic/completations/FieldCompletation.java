package com.lebastudios.sealcode.logic.completations;

import com.lebastudios.sealcode.util.Completation;

public class FieldCompletation extends Completation
{
    public FieldCompletation(String value)
    {
        super(value, "field.png");
    }

    @Override
    public String getDescription()
    {
        return "Field";
    }

    @Override
    public String getCompletation()
    {
        return getValue();
    }
}
