package com.lebastudios.stexteditor.applogic.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lebastudios.stexteditor.applogic.FileOperation;

import java.io.File;

public abstract class JSONSaveable<T>
{
    /**
     * Returns the file path of the JSON where the data is saved.
     * @return The file path.
     */
    public abstract String getFilePath();

    /**
     * Returns the static instance defined in the final class.
     * @return The static instance.
     */
    public abstract JSONSaveable<T> getInstance();

    /**
     * Creates a new static instance of the final class.
     * @return The new static instance.
     */
    public abstract T newInstance();
    
    public Thread preload()
    {
        Thread hilo = new Thread(getInstance()::load);
        hilo.start();
        return hilo;
    }

    /**
     * Loads the configuration file.
     */
    protected T load()
    {
        T instance;

        try
        {
            String content = FileOperation.readFile(new File(getFilePath()));
            //noinspection unchecked
            instance = (T) new Gson().fromJson(content, this.getClass());
        }
        catch (Exception e)
        {
            System.err.println(this.getClass().getName() +
                    " file not found");
            instance = newInstance();
            //noinspection unchecked
            new Thread(((JSONSaveable<T>) instance)::save).start();
        }
        
        return instance;
    }

    /**
     * Saves the configuration file.
     */
    public void save()
    {
        try
        {
            FileOperation.writeFile(new File(getFilePath()),
                    new GsonBuilder().setPrettyPrinting().create().toJson(this));
        }
        catch (Exception e)
        {
            System.err.println("Error saving " + 
                    this.getClass().getName() + " file.");
        }
    }
}
