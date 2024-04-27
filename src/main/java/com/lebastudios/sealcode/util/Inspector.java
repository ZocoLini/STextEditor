package com.lebastudios.sealcode.util;

import org.fxmisc.richtext.model.StyleSpans;

import java.util.Collection;

public abstract class Inspector
{
    protected static Inspector inspector = null;

    public static Inspector getInspector()
    {
        if (inspector == null)
        {
            throw new IllegalCallerException("No ha sido definido un inspector global");
        }

        return inspector;
    }
    
    public abstract StyleSpans<Collection<String>> inspect(String text, String fileExtension);
}
