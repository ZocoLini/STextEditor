package com.lebastudios.sealcode.OSManager.console;

import com.lebastudios.sealcode.OSManager.os.OperativeSystem;

public class ComConsole
{
    public static Process executeCommand(String commando)
    {
        return OperativeSystem.getActualOS().executeCommand(commando);
    }
}
