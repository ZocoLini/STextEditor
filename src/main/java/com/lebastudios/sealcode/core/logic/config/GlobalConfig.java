package com.lebastudios.sealcode.core.logic.config;

import com.lebastudios.sealcode.events.AppEvents;
import com.lebastudios.sealcode.core.logic.JSONSaveable;

public class GlobalConfig extends JSONSaveable<GlobalConfig>
{
    private static GlobalConfig instance;

    public EditorConfig editorConfig = new EditorConfig();
    public UserPrefs userPrefs = new UserPrefs();

    private GlobalConfig()
    {
        AppEvents.onSettingsUpdate.addListener(this::save);
    }

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

    public static GlobalConfig getStaticInstance()
    {
        if (instance == null)
        {
            instance = new GlobalConfig().load();
        }

        return instance;
    }

    @Override
    public GlobalConfig newInstance()
    {
        return new GlobalConfig();
    }

    public static class EditorConfig
    {
        public String theme = "Light";
        public String font = "Arial";
        public int fontSize = 13;
        public int indentation = 4;
        public int tabSize = 4;
    }

    public static class UserPrefs
    {
        public boolean ignoreGitDir = true;
        public String profile = "default";
    }
}
