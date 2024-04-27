package com.lebastudios.sealcode.logic.java.indexer;

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
    public void index(String string, String fileExtension)
    {
        switch (fileExtension)
        {
            case "java":
                JavaIndexer.getInstance().index(string);
                break;
            default:
        }
    }

    @Override
    public void unindex(String string, String fileExtension)
    {
        switch (fileExtension)
        {
            case "java":
                JavaIndexer.getInstance().unIndex(string);
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
