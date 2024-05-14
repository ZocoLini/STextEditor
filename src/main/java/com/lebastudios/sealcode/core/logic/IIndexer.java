package com.lebastudios.sealcode.core.logic;

import java.io.File;
import java.util.TreeSet;

public interface IIndexer
{
    void index(File file);

    void unIndex(File file);

    TreeSet<Completation> getCompletations(String word, File file);
}
