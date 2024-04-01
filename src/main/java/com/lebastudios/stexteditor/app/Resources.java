package com.lebastudios.stexteditor.app;

import com.lebastudios.stexteditor.TextEditorApplication;
import com.lebastudios.stexteditor.nodes.formateableText.FormateableText;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;

public final class Resources
{
    public static Image getImg(File file)
    {
        String extension = file.isDirectory() ? "directory" : FileOperation.getFileExtension(file);

        // Check if the extension has an img defined in the actual theme
        String path = FilePaths.getImgDirectory() + extension + ".png";
        if (existsResource(path))
        {
            return new Image(TextEditorApplication.class.getResourceAsStream(path));
        }

        System.out.println("The " + extension + " has no icon defined. Using Light theme icon");

        // Check if the extension has an img defined in the default theme
        path = FilePaths.getDefaultImgDirectory() + extension + ".css";
        if (existsResource(path))
        {
            return new Image(TextEditorApplication.class.getResourceAsStream(path));
        }

        System.out.println("The " + extension + " has no icon defined. Using default icon");

        // If the extension has no img defined, use the default img
        return new Image(TextEditorApplication.class.getResourceAsStream(FilePaths.getDefaultImgFile()));
    }
    
    public static String getExtensionStyle(String fileExtension)
    {
        // Check if the extension has a style defined in the actual theme
        String langStyleFile = FilePaths.getStyleDirectory() + fileExtension + ".css";
        if (existsResource(langStyleFile))
        {
            return TextEditorApplication.class.getResource(langStyleFile).toExternalForm();
        }

        System.out.println("The extension " + fileExtension + " has no theme style defined. Using Light theme style");

        // Check if the extension has a style defined in the default theme
        langStyleFile = FilePaths.getDefaultStyleDirectory() + fileExtension + ".css";
        if (existsResource(langStyleFile))
        {
            return TextEditorApplication.class.getResource(langStyleFile).toExternalForm();
        }

        System.out.println("The extension " + fileExtension + " has no style defined. Using default style");

        // If the extension has no style defined, use the default style
        return TextEditorApplication.class.getResource(FilePaths.getDefaultLangStyleFile()).toExternalForm();
    }
    
    public static String getLangCommonStyle()
    {
        return getExtensionStyle("commonLang");
    }
    
    public static String getCodeAreaStyle()
    {
        // Check if the code area has a style defined in the actual theme
        String codeAreaStylePath = FilePaths.getStyleDirectory() + "codeArea.css";
        if (existsResource(codeAreaStylePath))
        {
            return TextEditorApplication.class.getResource(codeAreaStylePath).toExternalForm();
        }

        System.out.println("The code area has no theme style defined. Using Light theme style");
        
        // Using the default style defined in the default theme
        codeAreaStylePath = FilePaths.getDefaultStyleDirectory() + "codeArea.css";
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
    
    private static boolean existsResource(String path)
    {
        return TextEditorApplication.class.getResource(path) != null;
    }
}
