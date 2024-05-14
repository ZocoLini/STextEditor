package com.lebastudios.sealcode.events;

import com.lebastudios.sealcode.core.logic.Completation;

import java.io.File;
import java.util.TreeSet;

public interface ICompletationsRequest
{
    void invoke(TreeSet<Completation> newCompletations, File file, String fileExtension,
                String lastWord, String text);
}
