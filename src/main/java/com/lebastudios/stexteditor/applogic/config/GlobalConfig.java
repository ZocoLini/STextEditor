package com.lebastudios.stexteditor.applogic.config;

import com.lebastudios.stexteditor.applogic.FilePaths;

public class GlobalConfig extends JSONSaveable<GlobalConfig>
{
    public static class EditorConfig
    {
        public String theme = "Light";
        public String font = "Arial";
        public int fontSize = 13;
        public int indentation = 4;
    }

    private static GlobalConfig instance;

    public static GlobalConfig getStaticInstance()
    {
        if (instance == null)
        {
            instance = new GlobalConfig().load();
        }

        return instance;
    }

    public String lang = "es";
    public String verion = "prototype";
    public EditorConfig editorConfig = new EditorConfig();

    private GlobalConfig() {}
    
    @Override
    public String getFilePath()
    {
        return FilePaths.getGlobalConfigDirectory() + "config.json";
    }

    @Override
    public JSONSaveable<GlobalConfig> getInstance()
    {
        return getStaticInstance();
    }

    @Override
    public GlobalConfig newInstance()
    {
        return new GlobalConfig();
    }
}
