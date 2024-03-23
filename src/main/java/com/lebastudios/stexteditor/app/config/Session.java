package com.lebastudios.stexteditor.app.config;

import com.lebastudios.stexteditor.app.FileOperation;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

public class Session
{
    private static final String SESSION_FILE = "session/lastSession.json";

    private static final JSONObject DEFAULT_SESSION =
            new JSONObject()
                    .put("filesOpen", new JSONArray())
                    .put("fileFilter", "none");

    private static Session instance;

    public static Session getInstance()
    {
        if (instance == null)
        {
            instance = new Session();
        }

        return instance;
    }

    private JSONObject session = DEFAULT_SESSION;

    private Session() {}

    public static Thread preload()
    {
        Thread hilo = new Thread(Session.getInstance()::load);
        hilo.start();
        return hilo;
    }

    /**
     * Loads the configuration file.
     */
    private void load()
    {
        String session = DEFAULT_SESSION.toString(2);

        try
        {
            String content = FileOperation.read(new File(SESSION_FILE));
            session = new JSONObject(content).toString(2);
        }
        catch (Exception e)
        {
            System.err.println("Session file not found. Using default session.");
        }
        
        try
        {
            this.session = new JSONObject(session);
        }
        catch (Exception exception)
        {
            System.err.println("Config file has a invalid format.");
            this.session = DEFAULT_SESSION;
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
            FileOperation.write(new File(SESSION_FILE), session.toString(2));
        }
        catch (Exception e)
        {
            System.err.println("Error saving session file.");
        }
    }

    // ************************************************
    // Getters and setters for the configuration values
    // ************************************************

    public JSONArray getFilesOpen()
    {
        return session.getJSONArray("filesOpen");
    }
}
