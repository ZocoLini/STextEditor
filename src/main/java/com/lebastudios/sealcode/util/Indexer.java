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
    
    public abstract void index(File file);

    public abstract void unindex(File file);
    
    public abstract TreeSet<Completation> getCompletation(String word, File file);
}
