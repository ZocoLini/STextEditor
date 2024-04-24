package com.lebastudios.sealcode.config;

import com.lebastudios.sealcode.util.JSONSaveable;

public class ProyectConfig extends JSONSaveable<ProyectConfig>
{
    private static ProyectConfig instance;
    
    private ProyectConfig() {}

    @Override
    public JSONSaveable<ProyectConfig> getInstance()
    {
        return getStaticInstance();
    }

    public static ProyectConfig getStaticInstance()
    {
        if (instance == null) instance = new ProyectConfig().load();

        return instance;
    }

    @Override
    public String getFilePath()
    {
        return FilePaths.getProyectConfigDirectory() + "config.json";
    }

    @Override
    public ProyectConfig newInstance()
    {
        return new ProyectConfig();
    }
}
