package com.lebastudios.sealcodeplugins.completations;

import com.lebastudios.sealcode.completations.Completation;
import com.lebastudios.sealcode.Indexer;
import com.lebastudios.sealcode.events.IEventMethod5;

import java.io.File;
import java.util.TreeSet;

public class CompletationsFilter implements IEventMethod5<TreeSet<Completation>, File, String, String, String>
{
    @Override
    public void invoke(TreeSet<Completation> newCompletations, File file, String fileExtension, 
                       String lastWord, String codeAreaText)
    {
        if (!fileExtension.equals("java")) return;

        newCompletations.addAll(Indexer.getIndexer().getCompletations(lastWord, file));
    }
}
