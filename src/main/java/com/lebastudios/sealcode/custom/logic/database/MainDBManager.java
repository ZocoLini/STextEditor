package com.lebastudios.sealcode.custom.logic.database;

import com.lebastudios.sealcode.core.logic.config.FilePaths;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.function.Function;

public class MainDBManager implements IDBManager<Connection>
{
    static String DATABASE_FOLDER = FilePaths.getAppDirectory() + "/database"; 
    
    private static MainDBManager instance;

    public static MainDBManager getInstance()
    {
        if (instance == null) instance = new MainDBManager();

        return instance;
    }

    private MainDBManager()
    {
    }

    private static final String HOST = "mjf.h.filess.io";
    private static final String PORT = "3307";
    private static final String DATABASE = "LebaStudios_hunterbut";
    private static final String USER = "LebaStudios_hunterbut";
    private static final String PASSWORD = "f400f8dba0ba4c58db0bcb7f0760ea7fdd1c4e7a";

    @Override
    public boolean connect(Function<Connection, Boolean> function)
    {
        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE, USER, PASSWORD))
        {
            if (function != null) return function.apply(connection);
            return true;
        }
        catch (Exception exception)
        {
            return false;
        }
    }
    
    public boolean logIn(String user, String passwordNotEncrypted)
    {
        return LogInUser.logIn(user, passwordNotEncrypted);
    }

    public boolean register(String text, String text1)
    {
        return LogInUser.register(text, text1);
    }

    public void logOut()
    {
        LogInUser.logOut();
    }

    public boolean isAnyAccountConnected()
    {
        return LogInUser.isAnyAccountConnected();
    }
}
