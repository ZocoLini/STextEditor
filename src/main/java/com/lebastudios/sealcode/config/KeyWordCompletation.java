package com.lebastudios.sealcode.config;

import com.lebastudios.sealcode.util.Completation;

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
