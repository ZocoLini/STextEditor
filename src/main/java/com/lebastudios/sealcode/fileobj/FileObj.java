package com.lebastudios.sealcode.fileobj;

import com.lebastudios.sealcode.events.AppEvents;
import com.lebastudios.sealcode.events.Event;
import com.lebastudios.sealcode.events.IEventMethod2;

import java.io.File;

public abstract class FileObj
{
    protected File representedFile;

    public Event onRead = new Event();
    public Event onWrite = new Event();

    private final IEventMethod2<String, FileObj> onFileObjSaved = (path, fileObj) ->
    {
        if (path.equals(representedFile.getAbsolutePath()) && fileObj != this) read();
    };

    public FileObj(File file)
    {
        representedFile = file;

        AppEvents.onFileObjModified.addListener(onFileObjSaved);
    }

    public final void read()
    {
        readLogic();

        onRead.invoke();
    }

    public abstract void readLogic();

    public final void write()
    {
        saveLogic();

        onWrite.invoke();

        AppEvents.onFileObjModified.invoke(representedFile.getAbsolutePath(), this);
    }

    public final void delete()
    {
        representedFile.delete();
        AppEvents.onFileObjModified.invoke(representedFile.getAbsolutePath(), this);
        AppEvents.onFileObjModified.removeListener(onFileObjSaved);
        representedFile = null;
    }

    public abstract void saveLogic();
}
