package com.lebastudios.stexteditor.exceptions;

public class ResourceNotLoadedException extends RuntimeException
{
    public ResourceNotLoadedException()
    {
    }

    public ResourceNotLoadedException(String message)
    {
        super(message);
    }
}
