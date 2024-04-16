package com.lebastudios.sealcode.applogic.config;

import com.lebastudios.sealcode.applogic.FilePaths;

public class ProyectConfig extends JSONSaveable<ProyectConfig>
{
    private static ProyectConfig instance;
    
    public static ProyectConfig getStaticInstance()
    {
        if (instance == null) instance = new ProyectConfig().load();
        
        return instance;
    }
    
    private ProyectConfig() {}
    
    @Override
    public String getFilePath()
    {
        return FilePaths.getProyectConfigDirectory() + "config.json";
    }

    @Override
    public JSONSaveable<ProyectConfig> getInstance()
    {
        return getStaticInstance();
    }

    @Override
    public ProyectConfig newInstance()
    {
        return new ProyectConfig();
    }
}
