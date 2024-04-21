package com.lebastudios.sealcode.applogic.completations;

public class KeyWordCompletation extends Completation
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
