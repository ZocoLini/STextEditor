package com.lebastudios.stexteditor.interfacecontrollers;

import com.lebastudios.stexteditor.app.config.Config;
import com.lebastudios.stexteditor.app.txtformatter.BracketHighlighter;
import com.lebastudios.stexteditor.app.txtformatter.KeyWordHighlighter;
import com.lebastudios.stexteditor.app.txtformatter.XMLHighlighting;
import com.lebastudios.stexteditor.interfacecontrollers.TabPaneController;
import com.lebastudios.stexteditor.nodes.formateableText.FormateableText;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class StyleController
{
    private static final String STYLE_PATH = "/css/styles/";
    
    @FXML
    private TabPane tabPane;
    
    public static void defaultStyle(FormateableText codeArea, String fileExtension)
    {
        String styleName = Config.getStaticInstance().editorConfig.styleName;
        String stylePath = STYLE_PATH + styleName + ".css";

        codeArea.getStylesheets().add(FormateableText.class
                .getResource(stylePath).toExternalForm());

        new BracketHighlighter(codeArea);
        new KeyWordHighlighter(codeArea, fileExtension);
        new XMLHighlighting(codeArea);
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
        
        for (var variable : tabPane.getTabs())
        {
            FormateableText codeArea = (FormateableText) variable.getContent();
            
            codeArea.getStylesheets().clear();
            codeArea.getStylesheets().add(FormateableText.class
                    .getResource(stylePath).toExternalForm());   
        }
    }
}