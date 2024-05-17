package com.lebastudios.sealcode.custom.logic.database;

// TODO: Implementar el Login

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainDBConnection
{
    private static MainDBConnection instance;
    
    public static MainDBConnection getInstance()
    {
        if (instance == null) instance = new MainDBConnection();
        
        return instance;
    }
    
    private MainDBConnection() {}
    
    private static final String HOST = "mjf.h.filess.io";
    private static final String PORT = "3307";
    private static final String DATABASE = "LebaStudios_hunterbut";
    private static final String USER = "LebaStudios_hunterbut";
    private static final String PASSWORD = "f400f8dba0ba4c58db0bcb7f0760ea7fdd1c4e7a";
    
    public boolean testConnection()
    {
        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE, USER, PASSWORD))
        {
            return true;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
