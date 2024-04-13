package com.lebastudios.stexteditor.applogic;

import com.lebastudios.stexteditor.TextEditorApplication;
import com.lebastudios.stexteditor.exceptions.ResourceNotLoadedException;
import javafx.scene.image.Image;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Resources
{
    private static final Map<String, Image> loadedImages = new HashMap<>();
    public static Image getImg(File file)
    {
        String extension = file.isDirectory() ? "directory" : FileOperation.getFileExtension(file);
        
        Image image = loadedImages.get(extension);
        
        if (image == null) 
        {
            return loadImg(extension);
        }
        
        return image;
    }
    
    private static Image loadImg(String extension)
    {
        Image image = null;
        
        // Check if the extension has an img defined in the actual theme
        String path = FilePaths.getImgDirectory() + extension + ".png";
        if (existsResource(path))
        {
            image = new Image(TextEditorApplication.class.getResourceAsStream(path));
        }

        // Check if the extension has an img defined in the default theme
        path = FilePaths.getDefaultImgDirectory() + extension + ".png";
        if (image == null && existsResource(path))
        {
            return new Image(TextEditorApplication.class.getResourceAsStream(path));
        }

        if (image == null) 
        {
            image = new Image(TextEditorApplication.class.getResourceAsStream(FilePaths.getDefaultImgFile()));
        }
        
        if (image == null) 
        {
            throw new ResourceNotLoadedException();
        }
        
        loadedImages.put(extension, image);
        
        // If the extension has no img defined, use the default img
        return image;
    }

    public static Image getIcon(String iconName)
    {
        String path = FilePaths.getIconDirectory() + iconName;
        if (existsResource(path))
        {
            return new Image(TextEditorApplication.class.getResourceAsStream(path));
        }

        path = FilePaths.getDefaultIconDirectory() + iconName;
        if (existsResource(path))
        {
            return new Image(TextEditorApplication.class.getResourceAsStream(path));
        }

        return new Image(TextEditorApplication.class.getResourceAsStream(FilePaths.getDefaultIconFile()));
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
        langStyleFile =
                FilePaths.getDefaultStyleDirectory() + FileOperation.toEquivalentFileExtension(fileExtension) + ".css";
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
            path = FilePaths.getProgLangSyntaxDirectory() + FileOperation.toEquivalentFileExtension(extension) +
                    ".json";
        }

        if (!file.exists() && !extension.equals("default"))
        {
            System.err.println("Error reading the syntax highlighting pattern file for the extension " +
                    extension + ". Default syntax highlighting will not be applied.");
            return getHighlightingRules("default");
        }

        try
        {
            rules = FileOperation.readFile(new File(path));
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
