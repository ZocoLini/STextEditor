package com.lebastudios.sealcode.core.logic.defaultcompletations;

import com.lebastudios.sealcode.core.logic.Completation;

public class KeyWordCompletation extends Completation
{
    public KeyWordCompletation()
    {
        super("keyword.png");
    }

    public KeyWordCompletation(String value)
    {
        super(value, "keyword.png");
    }
    
    @Override
    public String getDescription()
    {
        return "";
    }

    @Override
    public String getCompletation()
    {
        return getValue() + " ";
    }
}
