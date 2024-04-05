package com.lebastudios.stexteditor.OSManager.os;

import java.io.IOException;

class Windows implements IOperativeSystem
{
    @Override
    public void executeCommand(String commando)
    {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("cmd.exe", "/c", commando);
        builder.inheritIO();
        Process proceso = null;
        
        try
        {
            proceso = builder.start();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        
        try
        {
            proceso.waitFor();
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
}
