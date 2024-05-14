package com.lebastudios.sealcode.core.logic;

public abstract class Database
{
    protected static Database database;
    
    public static Database getDatabase()
    {
        if (database == null) 
        {
            throw new IllegalCallerException("No se ha inicializado ningun database.");
        }
        
        return database;
    }



    public abstract UserAccount Login(String name, String password);
}
