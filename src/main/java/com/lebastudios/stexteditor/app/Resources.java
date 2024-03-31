package com.lebastudios.stexteditor.app;

import com.lebastudios.stexteditor.TextEditorApplication;
import com.lebastudios.stexteditor.nodes.formateableText.FormateableText;
import javafx.scene.image.Image;

import java.io.File;

public final class Resources
{
    public static Image getImg(File file)
    {
        Image image;
        
        if (file.isDirectory())
        {
            image = new Image(TextEditorApplication.class.getResourceAsStream(
                    FilePaths.getImgDirectory() + "directory.png"
            ));
        }
        else
        {
            String extension = FileOperation.getFileExtension(file);

            String path = FilePaths.getImgDirectory() + extension + ".png";

            if (TextEditorApplication.class.getResourceAsStream(path) != null)
            {
                image = new Image(TextEditorApplication.class.getResourceAsStream(path));
            }
            else
            {
                image = new Image(TextEditorApplication.class.getResourceAsStream(
                        FilePaths.getDefaultImgDirectory() + "notfoundtype.png"
                ));
            }
        }
        
        return image;
    }
    
    public static String getExtensionStyle(String fileExtension)
    {
        String langStyleFile = FilePaths.getDefaultStyleDirectory() + "default.css";
        String extensionStyle = TextEditorApplication.class.getResource(langStyleFile).toExternalForm();
        
        try
        {
            langStyleFile = FilePaths.getStyleDirectory() + fileExtension + ".css";
            extensionStyle = TextEditorApplication.class.getResource(langStyleFile).toExternalForm();
        }
        catch (Exception e)
        {
            System.err.println("This extension has no style defined. Using default style");
        }

        
        return extensionStyle;
    }
    
    public static String getCodeAreaStyle()
    {
        String codeAreaStylePath = FilePaths.getStyleDirectory() + "codeArea.css";
        return TextEditorApplication.class.getResource(codeAreaStylePath).toExternalForm();
    }
    
    public static String getHighlightingRules(String extension)
    {
        String path = FilePaths.getProgLangSyntaxDirectory() + extension + ".json";
        
        String rules = "{}";
        
        File file = new File(path);
        
        if (!file.exists() && !extension.equals("default")) 
        {
            System.err.println("Error reading the syntax highlighting pattern file for the extension " + 
                    extension + ". Default syntax highlighting will not be applied.");
            return getHighlightingRules("default");
        }
        
        try
        {
            rules = FileOperation.read(new File(path));
        }
        catch (Exception e)
        {
            System.err.println("It couldnt be loaded any syntax highlighting pattern file. " +
                    "The syntax highlighting will not be applied.");

        }
        
        return rules;
    }
}
