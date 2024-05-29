package com.lebastudios.sealcode.config;

import com.lebastudios.sealcode.FilePaths;
import com.lebastudios.sealcode.FileOperation;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public final class Resources
{
    private static final Map<String, Image> loadedIcons = new HashMap<>();
    
    public static Image getIcon(String iconName)
    {
        Image image = loadedIcons.get(GlobalConfig.getStaticInstance().editorConfig.theme + iconName);

        if (image == null)
        {
            try
            {
                image = loadIcon(iconName);
            } catch (FileNotFoundException e)
            {
                throw new RuntimeException(e);
            }
            loadedIcons.put(GlobalConfig.getStaticInstance().editorConfig.theme + iconName, image);
            return image;
        }

        return image;
    }

    private static Image loadIcon(String iconName) throws FileNotFoundException
    {
        // Check if the image is defined in the actual theme
        File file = new File(FilePaths.getIconDirectory() + iconName);
        if (file.exists())
        {
            return new Image(new FileInputStream(file));
        }

        // Check if the image is defined in the default theme
        file = new File(FilePaths.getDefaultIconDirectory() + iconName);
        if (file.exists())
        {
            return new Image(new FileInputStream(file));
        }
        
        return new Image(new FileInputStream(FilePaths.getDefaultIconFile()));
    }

    public static String getLangCommonStyle()
    {
        return getExtensionStyle("commonLang");
    }

    public static String getExtensionStyle(String fileExtension)
    {
        // Check if the extension has a style defined in the actual theme
        File file = new File(FilePaths.getStyleDirectory() + fileExtension + ".css");
        if (file.exists())
        {
            try
            {
                return file.toURI().toURL().toExternalForm();
            } catch (MalformedURLException e)
            {
                throw new RuntimeException(e);
            }
        }

        // Check if the equivalent extension has a style defined in the actual theme
        file = new File(FilePaths.getStyleDirectory() + FileOperation.getEquivalentFileExtension(fileExtension) + ".css");
        if (file.exists())
        {
            try
            {
                return file.toURI().toURL().toExternalForm();
            } catch (MalformedURLException e)
            {
                throw new RuntimeException(e);
            }
        }

        // Check if the extension has a style defined in the default theme
        file = new File(FilePaths.getDefaultStyleDirectory() + fileExtension + ".css");
        if (file.exists())
        {
            try
            {
                return file.toURI().toURL().toExternalForm();
            } catch (MalformedURLException e)
            {
                throw new RuntimeException(e);
            }
        }

        // Check if the equivalent extension has a style defined in the default theme
        file = new File(FilePaths.getDefaultStyleDirectory() + FileOperation.getEquivalentFileExtension(fileExtension) + ".css");
        if (file.exists())
        {
            try
            {
                return file.toURI().toURL().toExternalForm();
            } catch (MalformedURLException e)
            {
                throw new RuntimeException(e);
            }
        }

        // If the extension has no style defined, use the default style
        try
        {
            return new File(FilePaths.getDefaultLangStyleFile()).toURI().toURL().toExternalForm();
        } catch (MalformedURLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static String getThemeStyle()
    {
        File file = new File(FilePaths.getStyleDirectory() + "theme.css");
        if (file.exists())
        {
            try
            {
                return file.toURI().toURL().toExternalForm();
            } catch (MalformedURLException e)
            {
                throw new RuntimeException(e);
            }
        }

        file = new File(FilePaths.getDefaultStyleDirectory() + "theme.css");
        try
        {
            return file.toURI().toURL().toExternalForm();
        } catch (MalformedURLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
