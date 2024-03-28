package com.lebastudios.stexteditor.app.config;

import java.util.ArrayList;
import java.util.List;

public class Session extends JSONSaveable<Session>
{
    public List<String> filesOpen = new ArrayList<>();
    public String fileFilter = "none";

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

    @Override
    public String getFilePath()
    {
        return "session/lastSession.json";
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
