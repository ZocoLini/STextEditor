package com.lebastudios.sealcode.applogic.txtformatter;

import com.lebastudios.sealcode.applogic.Resources;
import com.lebastudios.sealcode.frontend.fxextends.FormateableText;

public class StyleSetter
{
    public static void defaultStyle(FormateableText codeArea, String fileExtension)
    {
        // Añadimos el css del codeArea generico
        codeArea.getStylesheets().add(Resources.getCodeAreaStyle());

        // Añadimos el css común a la sintaxis de todos los lenguajes
        codeArea.getStylesheets().add(Resources.getLangCommonStyle());

        // Añadimos el css segun la extension del archivo
        codeArea.getStylesheets().add(Resources.getExtensionStyle(fileExtension));

        new BracketHighlighter(codeArea);
        new KeyWordHighlighter(codeArea, fileExtension);
    }
}