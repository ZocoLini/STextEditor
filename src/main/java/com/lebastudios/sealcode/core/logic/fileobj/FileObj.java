package com.lebastudios.sealcode.core.logic.fileobj;

import com.lebastudios.sealcode.events.AppEvents;

import java.io.File;

public abstract class FileObj
{
    protected File representedFile;
    
    public FileObj(File file)
    {
        representedFile = file;
        
        AppEvents.onFileObjSaved.addListener((path, fileObj) ->
        {
            if (path.equals(representedFile.getAbsolutePath()) && fileObj != this) read();
        });
    }
    
    public abstract void read();
    
    public final void write()
    {
        saveLogic();

        AppEvents.onFileObjSaved.invoke(representedFile.getAbsolutePath(), this);
    }
    
    public abstract void saveLogic();
}
