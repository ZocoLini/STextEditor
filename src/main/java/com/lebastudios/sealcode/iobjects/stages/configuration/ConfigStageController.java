package com.lebastudios.sealcode.iobjects.stages.configuration;

public class ConfigStageController
{
    private static ConfigStageController instance;

    public static ConfigStageController getInstance()
    {
        return instance;
    }

    public ConfigStageController()
    {
        instance = this;
    }
}
