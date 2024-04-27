package com.lebastudios.sealcode.util;

import java.io.File;
import java.util.TreeSet;

public interface ILangIndexer
{
    void index(String string);

    void unIndex(String string);

    TreeSet<Completation> getCompletations(String word, File file);
}
