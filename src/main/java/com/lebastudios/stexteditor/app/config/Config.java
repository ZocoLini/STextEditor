package com.lebastudios.stexteditor.app.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lebastudios.stexteditor.app.FileOperation;

import java.io.File;

public class Config
{
    private static final String CONFIG_FILE = "config/config.json";

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

    public static Config getInstance()
    {
        if (instance == null)
        {
            load();
        }

        return instance;
    }

    private Config() {}

    public static Thread preload()
    {
        Thread hilo = new Thread(Config::load);
        hilo.start();
        return hilo;
    }

    /**
     * Loads the configuration file.
     */
    private static void load()
    {
        Config config;

        try
        {
            String content = FileOperation.read(new File(CONFIG_FILE));
            config = new Gson().fromJson(content, Config.class);
        }
        catch (Exception e)
        {
            System.err.println("Config file not found. Using default configuration.");
            config = new Config();
        }

        new Thread(config::save).start();
        instance = config;
    }

    /**
     * Saves the configuration file.
     */
    public void save()
    {
        try
        {
            FileOperation.write(new File(CONFIG_FILE), 
                    new GsonBuilder().setPrettyPrinting().create().toJson(this));
        }
        catch (Exception e)
        {
            System.err.println("Error saving configuration file.");
        }
    }
}
