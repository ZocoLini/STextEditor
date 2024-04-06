package com.lebastudios.stexteditor.applogic.config.proyect;

import com.lebastudios.stexteditor.applogic.FilePaths;
import com.lebastudios.stexteditor.applogic.config.JSONSaveable;

import java.util.ArrayList;
import java.util.List;

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
