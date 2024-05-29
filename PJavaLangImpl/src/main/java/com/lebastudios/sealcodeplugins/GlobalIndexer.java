package com.lebastudios.sealcodeplugins;

import com.lebastudios.sealcode.completations.Completation;
import com.lebastudios.sealcode.FileOperation;
import com.lebastudios.sealcode.Indexer;

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
    public void unIndex(File file)
    {
        try
        {
            if (file.isDirectory())
            {
                for (var childFiles : file.listFiles())
                {
                    unIndex(childFiles);
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
    public TreeSet<Completation> getCompletations(String word, File file)
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
