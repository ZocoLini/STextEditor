package com.lebastudios.sealcodeplugins;

import com.lebastudios.sealcode.Inspector;
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