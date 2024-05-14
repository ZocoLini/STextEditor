package com.lebastudios.sealcode.core.logic;

import org.fxmisc.richtext.model.StyleSpans;

import java.util.Collection;

public interface IInspector
{
    StyleSpans<Collection<String>> inspect(String text, String fileExtension);
}
