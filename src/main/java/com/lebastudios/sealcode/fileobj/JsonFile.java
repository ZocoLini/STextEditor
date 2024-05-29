package com.lebastudios.sealcode.fileobj;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lebastudios.sealcode.FileOperation;

import java.io.File;

public class JsonFile<T> extends FileObj
{
    protected T data;
    
    public JsonFile(File file, T defaultInstance)
    {
        super(file);
        
        data = defaultInstance;
        
        read();
    }

    public T get()
    {
        return data;
    }
    
    @Override
    public void readLogic()
    {
        try
        {
            //noinspection unchecked
            data =  (T) new Gson().fromJson(FileOperation.readFile(representedFile), data.getClass());
        } catch (Exception e) {}
    }
    
    @Override
    public void saveLogic()
    {
        try
        {
            FileOperation.writeFile(representedFile, new GsonBuilder().setPrettyPrinting().create().toJson(data));
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
