package com.lebastudios.sealcode.custom.logic.database;

public class MainDBConnection
{
    private static MainDBConnection instance;
    
    public static MainDBConnection getInstance()
    {
        if (instance == null) instance = new MainDBConnection();
        
        return instance;
    }
    
    private MainDBConnection() {}
}
