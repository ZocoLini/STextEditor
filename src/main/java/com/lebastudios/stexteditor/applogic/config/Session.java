package com.lebastudios.stexteditor.applogic.config;

import com.lebastudios.stexteditor.applogic.FilePaths;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Session extends JSONSaveable<Session>
{
    public List<String> filesOpen = new ArrayList<>();
    public String fileFilter = "none";
    public String proyectDirectory = "";

    private static Session instance;

    public static Session getStaticInstance()
    {
        if (instance == null)
        {
            instance = new Session().load();
        }

        return instance;
    }

    private Session() {}

    public void reset()
    {
        filesOpen.clear();
        fileFilter = "none";
        proyectDirectory = "";
    }
    
    @Override
    public String getFilePath()
    {
        return FilePaths.getSessionDirectory() + "lastSession.json";
    }

    @Override
    public JSONSaveable<Session> getInstance()
    {
        return Session.getStaticInstance();
    }

    @Override
    public Session newInstance()
    {
        return new Session();
    }
}
