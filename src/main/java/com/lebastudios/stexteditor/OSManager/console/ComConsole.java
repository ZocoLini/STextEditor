package com.lebastudios.stexteditor.OSManager.console;

import com.lebastudios.stexteditor.OSManager.os.OperativeSystem;

public class ComConsole
{
    public static Process executeCommand(String commando)
    {
        return OperativeSystem.getActualOS().executeCommand(commando);
    }
}
