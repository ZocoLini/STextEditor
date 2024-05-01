package com.lebastudios.sealcode.logic;

import com.lebastudios.sealcode.logic.java.JavaIndexer;
import com.lebastudios.sealcode.util.Completation;
import com.lebastudios.sealcode.util.FileOperation;
import com.lebastudios.sealcode.util.Indexer;

import java.io.File;
import java.util.TreeSet;

public class GlobalIndexer extends Indexer
{
    public static void startIndexer()
    {
        Indexer.indexer = new GlobalIndexer();
    }
    
    private GlobalIndexer() {}
    
    @Override
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
                
                return;
            }
        } catch (Exception e)
        {
            System.out.println("Error reading: " + file + ". Maybe the file path wasn't correct. It couldn't be indexed");
            System.out.println(e.getMessage());
        }
        
        switch (FileOperation.getFileExtension(file))
        {
            case "java":
                JavaIndexer.getInstance().index(file);
                break;
            default:
        }
    }

    @Override
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

                return;
            }
        } catch (Exception e)
        {
            System.out.println("Error reading: " + file + ". Maybe the file path wasn't correct. It couldn't be indexed");
            System.out.println(e.getMessage());
        }

        switch (FileOperation.getFileExtension(file))
        {
            case "java":
                JavaIndexer.getInstance().unIndex(file);
                break;
            default:
        }
    }

    @Override
    public TreeSet<Completation> getCompletation(String word, File file)
    {
        switch (FileOperation.getFileExtension(file))
        {
            case "java":
                return JavaIndexer.getInstance().getCompletations(word, file);
            default:
                return new TreeSet<>();
        }
    }
}
