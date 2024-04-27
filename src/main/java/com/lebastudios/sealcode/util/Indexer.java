package com.lebastudios.sealcode.util;

import java.io.File;
import java.util.TreeSet;

public abstract class Indexer
{
    protected static Indexer indexer = null;
    
    public static Indexer getIndexer()
    {
        if (indexer == null) 
        {
            throw new IllegalCallerException("No se ha inicializado ningun indexer.");
        }
        
        return indexer;
    }
    
    public abstract void index(String string, String fileExtension);

    public void index(File file)
    {
        try
        {
            if (file.isDirectory())
            {
                for (var childFiles : file.listFiles())
                {
                    index(childFiles);
                }
            }
            else
            {
                index(FileOperation.readFile(file), FileOperation.getFileExtension(file));   
            }
        } catch (Exception e)
        {
            System.out.println("Error reading: " + file + ". Maybe the file path wasn't correct. It couldn't be indexed");
            System.out.println(e.getMessage());
        }
    }

    public abstract void unindex(String string, String fileExtension);

    public void unindex(File file)
    {
        try
        {
            if (file.isDirectory())
            {
                for (var childFiles : file.listFiles())
                {
                    unindex(childFiles);
                }
            }
            else
            {
                unindex(FileOperation.readFile(file), FileOperation.getFileExtension(file));
            }
        } catch (Exception e)
        {
            System.out.println("Error reading: " + file + ". Maybe the file path wasn't correct. It couldn't be indexed");
        }
    }
    
    public abstract TreeSet<Completation> getCompletation(String word, File file);
}
