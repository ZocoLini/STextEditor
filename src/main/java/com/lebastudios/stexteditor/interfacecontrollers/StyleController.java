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
        
        // TODO: Estamos cambiando como se leen los estilos

        codeArea.getStylesheets().add(FormateableText.class
                .getResource(langStylePath).toExternalForm());
        
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