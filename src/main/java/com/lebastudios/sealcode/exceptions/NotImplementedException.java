package com.lebastudios.sealcode.exceptions;

public class NotImplementedException extends RuntimeException
{
    public NotImplementedException()
    {
        super("Not implemented yet");
    }
    
    public NotImplementedException(String message)
    {
        super(message);
    }
}
