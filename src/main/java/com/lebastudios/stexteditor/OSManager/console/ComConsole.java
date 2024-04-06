package com.lebastudios.stexteditor.OSManager.console;

import com.lebastudios.stexteditor.OSManager.os.OperativeSystem;

public class ComConsole
{
    public static void executeCommand(String commando)
    {
        OperativeSystem.getActualOS().executeCommand(commando);
    }
}
