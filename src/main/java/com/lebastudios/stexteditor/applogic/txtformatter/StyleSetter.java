package com.lebastudios.stexteditor.applogic.txtformatter;

import com.lebastudios.stexteditor.applogic.Resources;
import com.lebastudios.stexteditor.iobjects.nodes.FormateableText;

public class StyleSetter
{
    public static void defaultStyle(FormateableText codeArea, String fileExtension)
    {
        // Añadimos el css segun la extension del archivo
        codeArea.getStylesheets().add(Resources.getExtensionStyle(fileExtension));
        
        // Añadimos el css común a la sintaxis de todos los lenguajes
        codeArea.getStylesheets().add(Resources.getLangCommonStyle());
        
        // Añadimos el css del codeArea generico
        codeArea.getStylesheets().add(Resources.getCodeAreaStyle());

        new BracketHighlighter(codeArea);
        new KeyWordHighlighter(codeArea, fileExtension);
    }
}