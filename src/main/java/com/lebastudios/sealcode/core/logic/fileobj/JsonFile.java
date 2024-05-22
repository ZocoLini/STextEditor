package com.lebastudios.sealcode.core.logic.fileobj;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lebastudios.sealcode.global.FileOperation;

import java.io.File;
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
    
    public void readFromFile()
    {
        try
        {
            //noinspection unchecked
            instance =  (T) new Gson().fromJson(FileOperation.readFile(representedFile), instance.getClass());
        } catch (Exception e)
        {
            System.err.println("Error reading " + this.getClass().getName() + " file.");
            instance = getDefaultInstance();
        }
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

    public JsonFile<T> createNewFile(File directory, String name)
    {
        File file = new File(directory, name + ".json");

        JsonFile<T> jsonFile = new JsonFile<>(file, getDefaultInstance());

        jsonFile.writeToFile();

        return jsonFile;
    }
    
    private T getDefaultInstance()
    {
        try
        {
            //noinspection unchecked
            return (T) Arrays.stream(instance.getClass().getConstructors())
                    .filter(constructor -> constructor.getParameterCount() == 0).findFirst().get().newInstance();
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
