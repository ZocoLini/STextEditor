package com.lebastudios.stexteditor.interfacecontrollers;

import com.lebastudios.stexteditor.app.Resources;
import com.lebastudios.stexteditor.app.txtformatter.BracketHighlighter;
import com.lebastudios.stexteditor.app.txtformatter.KeyWordHighlighter;
import com.lebastudios.stexteditor.nodes.formateableText.FormateableText;

public class StyleController
{
    public static void defaultStyle(FormateableText codeArea, String fileExtension)
    {
        // Añadimos el css segun la extension del archivo
        codeArea.getStylesheets().add(Resources.getExtensionStyle(fileExtension));
        
        // Añadimos el css del codeArea generico
        codeArea.getStylesheets().add(Resources.getCodeAreaStyle());

        new BracketHighlighter(codeArea);
        new KeyWordHighlighter(codeArea, fileExtension);
    }
}