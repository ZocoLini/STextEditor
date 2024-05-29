package com.lebastudios.sealcode;

import com.lebastudios.sealcode.completations.Completation;

import java.io.File;
import java.util.TreeSet;

public interface IIndexer
{
    void index(File file);

    void unIndex(File file);

    TreeSet<Completation> getCompletations(String word, File file);
}
