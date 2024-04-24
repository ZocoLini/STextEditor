package com.lebastudios.sealcode.core.os;

public class ComConsole
{
    public static Process executeCommand(String commando)
    {
        return OperativeSystem.getActualOS().executeCommand(commando);
    }
}
