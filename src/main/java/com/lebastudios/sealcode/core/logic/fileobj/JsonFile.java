package com.lebastudios.sealcode.core.logic.fileobj;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lebastudios.sealcode.global.FileOperation;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class JsonFile<T> extends FileObj
{
    protected T instance;
    
    public JsonFile(File file, T defaultInstance)
    {
        super(file);
        
        instance = defaultInstance;
        
        readFromFile();
    }

    public T getInstance()
    {
        return instance;
    }
    
    // TODO: A lo mejor hacer un evento para cuando se guarde un archivo, si otro tiene el mismo archivo cargado, se actualice
    
    public void readFromFile()
    {
        try
        {
            //noinspection unchecked
            instance =  (T) new Gson().fromJson(FileOperation.readFile(representedFile), instance.getClass());
        } catch (Exception e) {}
    }
    
    public void writeToFile()
    {
        try
        {
            FileOperation.writeFile(representedFile, new GsonBuilder().setPrettyPrinting().create().toJson(instance));
        }
        catch (Exception e)
        {
            System.err.println("Error saving " + this.getClass().getName() + " file.");
        }
    }

    public static File createNewFile(File directory, String name)
    {
        File file = new File(directory, name + ".json");

        try
        {
            file.createNewFile();
            FileOperation.writeFile(file, "{}");
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return file;
    }

    public static File createNewFile(String directory, String name)
    {
        File file = new File(directory, name + ".json");

        try
        {
            file.createNewFile();
            FileOperation.writeFile(file, "{}");
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return file;
    }
}
