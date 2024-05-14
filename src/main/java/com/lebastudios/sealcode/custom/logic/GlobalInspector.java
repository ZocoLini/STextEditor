package com.lebastudios.sealcode.custom.logic;

import com.lebastudios.sealcode.custom.logic.java.JavaInspector;
import com.lebastudios.sealcode.core.logic.Inspector;
import org.fxmisc.richtext.model.StyleSpans;

import java.util.Collection;

public class GlobalInspector extends Inspector
{
    public static void startInspector()
    {
        inspector = new GlobalInspector();
    }

    private GlobalInspector() {}

    @Override
    public StyleSpans<Collection<String>> inspect(String text, String fileExtension)
    {
        switch (fileExtension) 
        {
            case "java":
                return JavaInspector.getInstance().inspect(text, fileExtension);
            default:
                return null;
        }
    }
}