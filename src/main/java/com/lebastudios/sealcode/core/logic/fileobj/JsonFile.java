package com.lebastudios.sealcode.core.logic.fileobj;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lebastudios.sealcode.global.FileOperation;

import java.io.File;

public abstract class JsonFile extends FileObj
{
    public JsonFile(File file)
    {
        super(file);
    }

    @Override
    public JsonFile readFromFile()
    {
        try
        {
            return new Gson().fromJson(FileOperation.readFile(representedFile), this.getClass());
        } catch (Exception e)
        {
            return null;
        }
    }
    
    @Override
    public void writeToFile()
    {
        try
        {
            FileOperation.writeFile(representedFile, new GsonBuilder().setPrettyPrinting().create().toJson(this));
        }
        catch (Exception e)
        {
            System.err.println("Error saving " + this.getClass().getName() + " file.");
        }
    }
}
