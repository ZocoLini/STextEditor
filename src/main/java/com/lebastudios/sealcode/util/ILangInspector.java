package com.lebastudios.sealcode.util;

import org.fxmisc.richtext.model.StyleSpans;

import java.util.Collection;

public interface ILangInspector
{
    StyleSpans<Collection<String>> inspect(String codeToInspect);
}
