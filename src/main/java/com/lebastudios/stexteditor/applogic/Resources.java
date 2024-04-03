package com.lebastudios.stexteditor.applogic;

import com.lebastudios.stexteditor.TextEditorApplication;
import javafx.scene.image.Image;

import java.io.File;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        // Check if the extension has an img defined in the default theme
        path = FilePaths.getDefaultImgDirectory() + extension + ".css";
        if (existsResource(path))
        {
            return new Image(TextEditorApplication.class.getResourceAsStream(path));
        }

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

        // Check if the equivalent extension has a style defined in the actual theme
        langStyleFile = FilePaths.getStyleDirectory() + FileOperation.toEquivalentFileExtension(fileExtension) + ".css";
        if (existsResource(langStyleFile))
        {
            return TextEditorApplication.class.getResource(langStyleFile).toExternalForm();
        }
        
        // Check if the extension has a style defined in the default theme
        langStyleFile = FilePaths.getDefaultStyleDirectory() + fileExtension + ".css";
        if (existsResource(langStyleFile))
        {
            return TextEditorApplication.class.getResource(langStyleFile).toExternalForm();
        }

        // Check if the equivalent extension has a style defined in the default theme
        langStyleFile = FilePaths.getDefaultStyleDirectory() + FileOperation.toEquivalentFileExtension(fileExtension) + ".css";
        if (existsResource(langStyleFile))
        {
            return TextEditorApplication.class.getResource(langStyleFile).toExternalForm();
        }
        
        // If the extension has no style defined, use the default style
        return TextEditorApplication.class.getResource(FilePaths.getDefaultLangStyleFile()).toExternalForm();
    }
    
    private static String getClassStyleFromFile(String resourcePath, String className)
    {
        String content = null;
        
        try
        {
            content = FileOperation.readResource(resourcePath);
        }
        catch (Exception e)
        {
            System.out.println("Error in Resources.java reading the file " + resourcePath + ". The class style will " +
                    "not be applied.");
        }
        
        Matcher matcher = Pattern.compile("\\." + className + "\\s*\\{([^}]*)\\}").matcher(content);
        
        if (matcher.find())
        {
            String classFound = matcher.group();
            
            classFound = classFound.replace("." + className, "").replace("{", "")
                    .replace("}", "").trim().replaceAll("\\s+", " ");
            
            return classFound;
        }
        
        if (!Objects.equals(resourcePath, FilePaths.getDefaulThemeFile())) 
        {
            return getClassStyleFromFile(FilePaths.getDefaulThemeFile(), className);
        }
        
        return "";
    }
    
    public static String getThemeClassStyleFromFile(String className)
    {
        return getClassStyleFromFile(FilePaths.getStyleDirectory() + "theme.css", className);
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
        
        // Using the default style defined in the default theme
        codeAreaStylePath = FilePaths.getDefaultStyleDirectory() + "codeArea.css";
        return TextEditorApplication.class.getResource(codeAreaStylePath).toExternalForm();
    }
    
    public static String getThemeStyle()
    {
        String themeStylePath = FilePaths.getStyleDirectory() + "theme.css";
        if (existsResource(themeStylePath)) 
        {
            return TextEditorApplication.class.getResource(themeStylePath).toExternalForm();
        }
        
        themeStylePath = FilePaths.getDefaultStyleDirectory() + "theme.css";
        return TextEditorApplication.class.getResource(themeStylePath).toExternalForm();
    }
    
    public static String getHighlightingRules(String extension)
    {
        String path = FilePaths.getProgLangSyntaxDirectory() + extension + ".json";
        
        String rules = "{}";
        
        File file = new File(path);
        
        if (!file.exists()) 
        {
            path = FilePaths.getProgLangSyntaxDirectory() + FileOperation.toEquivalentFileExtension(extension) + ".json";
        }
        
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
