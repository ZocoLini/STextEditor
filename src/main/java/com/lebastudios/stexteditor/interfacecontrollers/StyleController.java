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
        String styleName = Config.getStaticInstance().editorConfig.styleName;
        String stylePath = STYLE_PATH + styleName + ".css";

        codeArea.getStylesheets().add(FormateableText.class
                .getResource(stylePath).toExternalForm());

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
    
    public void changueDefaultStyle(String styleName)
    {
        Config.getStaticInstance().editorConfig.styleName = styleName;

        String stylePath = STYLE_PATH + styleName + ".css";
        
        for (var variable : TabPaneController.getInstance().tabPane.getTabs())
        {
            FormateableText codeArea = (FormateableText) variable.getContent();
            
            codeArea.getStylesheets().clear();
            codeArea.getStylesheets().add(FormateableText.class
                    .getResource(stylePath).toExternalForm());   
        }
    }
}