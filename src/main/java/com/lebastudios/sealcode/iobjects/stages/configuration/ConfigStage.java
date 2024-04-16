package com.lebastudios.sealcode.iobjects.stages.configuration;

public class ConfigStage
{
    private static ConfigStage instance;

    public static ConfigStage getInstance()
    {
        if (instance == null) instance = new ConfigStage();

        return instance;
    }

    private ConfigStage()
    {
        super();
    }
}
