package com.lebastudios.stexteditor.app.txtformatter;

import com.lebastudios.stexteditor.app.config.Config;
import com.lebastudios.stexteditor.nodes.formateableText.FormatteableText;

public class ApplyFormat
{
    private static final String STYLE_PATH = "/css/styles/";
    
    public static void defaultStyle(FormatteableText codeArea)
    {
        String styleName = Config.getStaticInstance().editorConfig.styleName;
        String stylePath = STYLE_PATH + styleName + ".css";

        codeArea.getStylesheets().add(FormatteableText.class
                .getResource(stylePath).toExternalForm());

        new BracketHighlighter(codeArea);
        new KeyWordHighlighter(codeArea);
        new XMLHighlighting(codeArea);
    }
}