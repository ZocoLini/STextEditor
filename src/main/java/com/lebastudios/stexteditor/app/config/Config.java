package com.lebastudios.stexteditor.app.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lebastudios.stexteditor.app.FileOperation;

import java.io.File;

public class Config extends JSONSaveable<Config>
{
    public String lang = "es";
    public String verion = "prototype";
    public EditorConfig editorConfig = new EditorConfig();

    public static class EditorConfig
    {
        public String theme;
        public String font;
        public int fontSize;
        public boolean lineNumbers;
        public boolean wrapText;
        public boolean autoSave;
        public int indentation;
    }

    private static Config instance;

    public static Config getStaticInstance()
    {
        if (instance == null)
        {
            instance = new Config();
            instance.load();
        }

        return instance;
    }

    private Config() {}
    
    @Override
    public String getFilePath()
    {
        return "config/config.json";
    }

    @Override
    public JSONSaveable<Config> getInstance()
    {
        return Config.getStaticInstance();
    }

    @Override
    public void setInstance(Config session)
    {
        instance = session;
    }

    @Override
    public Config newInstance()
    {
        return new Config();
    }
}
