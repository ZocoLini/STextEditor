package com.lebastudios.sealcode.core.logic.config;

import com.lebastudios.sealcode.core.logic.Completation;

class KeyWordCompletation extends Completation
{
    public KeyWordCompletation()
    {
        super("keyword.png");
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
