package com.lebastudios.stexteditor.interfacecontrollers;

import com.lebastudios.stexteditor.app.config.Config;
import com.lebastudios.stexteditor.app.txtformatter.BracketHighlighter;
import com.lebastudios.stexteditor.app.txtformatter.KeyWordHighlighter;
import com.lebastudios.stexteditor.nodes.formateableText.FormateableText;

public class StyleController
{
    private static final String STYLE_PATH = "/css/styles/";
    
    public static void defaultStyle(FormateableText codeArea, String fileExtension)
    {
        String style = Config.getStaticInstance().editorConfig.style;
        String stylePath = STYLE_PATH + style + "/";
        
        String langStylePath = stylePath + fileExtension + ".css";
        String codeAreaStylePath = stylePath + "codeArea.css";
        
        // Añadimos el css segun la extension del archivo
        try
        {
            codeArea.getStylesheets().add(FormateableText.class
                    .getResource(langStylePath).toExternalForm());
        }
        catch (Exception e)
        {
            System.err.println("This extension has no style");
        }
        
        // Añadimos el css del codeArea generico
        codeArea.getStylesheets().add(FormateableText.class
                .getResource(codeAreaStylePath).toExternalForm());

        new BracketHighlighter(codeArea);
        new KeyWordHighlighter(codeArea, fileExtension);
    }
    
    public void setStyle(FormateableText codeArea, String styleName)
    {
        String stylePath = STYLE_PATH + styleName + ".css";
        
        codeArea.getStylesheets().clear();
        codeArea.getStylesheets().add(FormateableText.class
                .getResource(stylePath).toExternalForm());
    }
}