package com.lebastudios.stexteditor.OSManager.os;

import com.lebastudios.stexteditor.exceptions.NotImplementedException;

class Linux implements IOperativeSystem
{
    @Override
    public void executeCommand(String commando)
    {
        throw new NotImplementedException();
    }
}
