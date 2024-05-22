package com.lebastudios.sealcode.core.logic.fileobj;

import java.io.File;

public abstract class FileObj
{
    protected File representedFile;
    
    public FileObj(File file)
    {
        representedFile = file;
    }
    
    /*
    public abstract FileObj readFromFile();
    
    public abstract void writeToFile();
    
    public abstract FileObj createNewFile(File directory, String styleClass);
    */
}
