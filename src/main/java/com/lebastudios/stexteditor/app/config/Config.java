package com.lebastudios.stexteditor.app.config;

public class Config extends JSONSaveable<Config>
{
    public String lang = "es";
    public String verion = "prototype";
    public EditorConfig editorConfig = new EditorConfig();

    public static class EditorConfig
    {
        public String themeName;
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
