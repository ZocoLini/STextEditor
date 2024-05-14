package com.lebastudios.sealcode.custom.logic;

import com.lebastudios.sealcode.core.logic.Database;
import com.lebastudios.sealcode.core.logic.UserAccount;

public class MainDatabase extends Database
{
    public static void startDatabase() { database = new MainDatabase(); }
    
    private MainDatabase() {}

    @Override
    public UserAccount Login(String name, String password)
    {
        return null;
    }
}
