package com.lebastudios.sealcode.util;

import java.io.File;
import java.util.TreeSet;

public interface ILangIndexer
{
    void index(File file);

    void unIndex(File file);

    TreeSet<Completation> getCompletations(String word, File file);
}
