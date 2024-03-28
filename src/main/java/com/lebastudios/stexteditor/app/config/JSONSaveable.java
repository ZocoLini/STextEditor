package com.lebastudios.stexteditor.app.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lebastudios.stexteditor.app.FileOperation;

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
     * Sets the static instance defined in the final class.
     * @param session The instance to set.
     */
    public abstract void setInstance(T session);

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
    protected void load()
    {
        T instance;

        try
        {
            String content = FileOperation.read(new File(getFilePath()));
            instance = (T) new Gson().fromJson(content, getInstance().getClass());
        }
        catch (Exception e)
        {
            System.err.println(this.getClass().getName() +
                    " file not found. Using default configuration.");
            instance = newInstance();
        }

        new Thread(getInstance()::save).start();
        setInstance(instance);
    }

    /**
     * Saves the configuration file.
     */
    public void save()
    {
        try
        {
            FileOperation.write(new File(getFilePath()),
                    new GsonBuilder().setPrettyPrinting().create().toJson(this));
        }
        catch (Exception e)
        {
            System.err.println("Error saving " + 
                    this.getClass().getName() + " file.");
        }
    }
}
