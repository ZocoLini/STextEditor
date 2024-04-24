package com.lebastudios.sealcode.core.os;

import java.io.IOException;

public interface IOperativeSystem
{
    default Process executeCommand(String commando)
    {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("bash", "-c", commando);
        builder.inheritIO();
        Process proceso;

        try
        {
            proceso = builder.start();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        return proceso;
    }

    default String fileSeparator() { return "/"; }
}
