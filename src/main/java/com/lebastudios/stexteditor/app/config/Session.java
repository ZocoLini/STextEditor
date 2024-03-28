package com.lebastudios.stexteditor.app.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lebastudios.stexteditor.app.FileOperation;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Session
{
    private static final String SESSION_FILE = "session/lastSession.json";
    
    public List<String> filesOpen = new ArrayList<>();
    public String fileFilter = "none";

    private static Session instance;

    public static Session getInstance()
    {
        if (instance == null)
        {
            instance = new Session();
            instance.load();
        }

        return instance;
    }

    public static void setInstance(Session session)
    {
        instance = session;
    }
    
    private Session() {}

    public Thread preload()
    {
        Thread hilo = new Thread(getInstance()::load);
        hilo.start();
        return hilo;
    }

    /**
     * Loads the configuration file.
     */
    private void load()
    {
        Session session;
        
        try
        {
            String content = FileOperation.read(new File(SESSION_FILE));
            session = new Gson().fromJson(content, Session.class);
        }
        catch (Exception e)
        {
            System.err.println("Session file not found. Using default configuration.");
            session = new Session();
        }
        
        new Thread(session::save).start();
        setInstance(session);
    }

    /**
     * Saves the configuration file.
     */
    public void save()
    {
        try
        {
            FileOperation.write(new File(SESSION_FILE), 
                    new GsonBuilder().setPrettyPrinting().create().toJson(this));
        }
        catch (Exception e)
        {
            System.err.println("Error saving session file.");
        }
    }
}
