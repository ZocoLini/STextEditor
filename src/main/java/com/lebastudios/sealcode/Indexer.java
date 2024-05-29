package com.lebastudios.sealcode;

public abstract class Indexer implements IIndexer
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
}
