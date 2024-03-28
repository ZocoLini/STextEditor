package com.lebastudios.stexteditor.app.config;

import com.lebastudios.stexteditor.app.FileOperation;
import org.json.JSONObject;

import java.io.File;

public class Config
{
    private static final String CONFIG_FILE = "config/config.json";

    private static final JSONObject DEFAULT_CONFIG =
            new JSONObject()
                    .put("lang", "es")
                    .put("version", "prototype")
                    .put("editor", new JSONObject()
                            .put("theme", "light")
                            .put("font", "Arial")
                            .put("fontSize", 13)
                            .put("lineNumbers", true)
                            .put("wrapText", false)
                            .put("autoSave", true)
                            .put("indentation", 4)
                    );

    private static Config instance;

    public static Config getInstance()
    {
        if (instance == null)
        {
            instance = new Config();
        }

        return instance;
    }

    private JSONObject configuration = DEFAULT_CONFIG;

    private Config() {}

    public static Thread preload()
    {
        Thread hilo = new Thread(Config.getInstance()::load);
        hilo.start();
        return hilo;
    }

    /**
     * Loads the configuration file.
     */
    private void load()
    {
        String config = DEFAULT_CONFIG.toString(2);

        try
        {
            String content = FileOperation.read(new File(CONFIG_FILE));
            config = new JSONObject(content).toString(2);
        }
        catch (Exception e)
        {
            System.err.println("Config file not found. Using default configuration.");
        }

        try
        {
            configuration = new JSONObject(config);
        }
        catch (Exception exception)
        {
            System.err.println("Config file has a invalid format.");
            configuration = DEFAULT_CONFIG;
        }

        new Thread(this::save).start();
    }

    /**
     * Saves the configuration file.
     */
    public void save()
    {
        try
        {
            FileOperation.write(new File(CONFIG_FILE), configuration.toString(2));
        }
        catch (Exception e)
        {
            System.err.println("Error saving configuration file.");
        }
    }

    // ************************************************
    // Getters and setters for the configuration values
    // ************************************************
    
    public String getFont()
    {
        return configuration.getJSONObject("editor").getString("font");
    }
    
    public void setFont(String font)
    {
        configuration.getJSONObject("editor").put("font", font);
    }
    
    public int getFontSize()
    {
        return configuration.getJSONObject("editor").getInt("fontSize");
    }
    
    public void setFontSize(int fontSize)
    {
        configuration.getJSONObject("editor").put("fontSize", fontSize);
    }
}
