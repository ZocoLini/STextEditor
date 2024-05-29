package com.lebastudios.sealcode.config;

import com.lebastudios.sealcode.FilePaths;
import com.lebastudios.sealcode.JSONSaveable;

import java.util.ArrayList;
import java.util.List;

public class Session extends JSONSaveable<Session>
{
    private static Session instance;
    
    public List<String> filesOpen = new ArrayList<>();
    public String proyectDirectory = "";
    
    private Session() {}

    public void reset()
    {
        filesOpen.clear();
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
        return getStaticInstance();
    }

    public static Session getStaticInstance()
    {
        if (instance == null)
        {
            instance = new Session().load();
        }

        return instance;
    }

    @Override
    public Session newInstance()
    {
        return new Session();
    }
}
