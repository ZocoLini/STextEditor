package com.lebastudios.stexteditor.app.txtformatter;

import com.lebastudios.stexteditor.app.FileOperation;
import com.lebastudios.stexteditor.app.config.Config;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;

import java.io.File;

public class ApplyFormat
{
    private static final String FORMAT_STYLE_DIRECTORY = "config/styles/";

    public static void defaultStyle(TextArea textArea)
    {
        textArea.setStyle("-fx-font-family: " + Config.getInstance().getFont() + ";" +
                " -fx-font-size: " + Config.getInstance().getFontSize() + ";");
    }
    
    public static TextFormatter<TextFormatter.Change> textFormatter()
    {
        return new TextFormatter<>(change ->
        {
            if (change.isAdded())
            {
                String text = change.getControlNewText();
                String[] lines = text.split("\n");
                
                for (int i = 0; i < lines.length; i++)
                {
                    if (lines[i].length() > 1)
                    {
                        lines[i] = lines[i].substring(1);
                    }
                }
                
                change.setText(String.join("\n", lines));
            }
            System.out.println(123);
            return change;
        });
    }
    
    public static void applyColorsToKeyWords(TextArea textArea)
    {
        
    }
    
    public static void byExtension(TextArea textArea, String extension)
    {
        // Aplica el css correspondiente al archivo
        String style;
        
        try
        {
            style = FileOperation.read(new File(FORMAT_STYLE_DIRECTORY + extension + ".css"));
            textArea.setStyle(style);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        
        // Resalta la sintaxis del archivo
    }
}
