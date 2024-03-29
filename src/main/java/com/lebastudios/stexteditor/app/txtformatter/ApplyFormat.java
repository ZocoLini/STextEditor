package com.lebastudios.stexteditor.app.txtformatter;

import com.lebastudios.stexteditor.app.config.Config;
import com.lebastudios.stexteditor.nodes.formateableText.FormatteableText;

public class ApplyFormat
{
    private static final String STYLE_PATH = "/css/styles/";
    
    private String[] keywords = new String[] {
            "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp",
            "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void",
            "volatile", "while"
    };
    
    public static void defaultStyle(FormatteableText codeArea)
    {
        String styleName = Config.getStaticInstance().editorConfig.styleName;
        String stylePath = STYLE_PATH + styleName + ".css";

        codeArea.getStylesheets().add(FormatteableText.class
                .getResource(stylePath).toExternalForm());

        new BracketHighlighter(codeArea);
        new KeyWordHighlighter(codeArea);
    }
}