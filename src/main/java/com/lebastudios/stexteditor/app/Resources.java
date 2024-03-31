package com.lebastudios.stexteditor.app;

import javafx.scene.image.Image;

import java.io.File;

public final class Resources
{
    public static Image getImg(File file, Object resouceHandler)
    {
        Image image;
        
        if (file.isDirectory())
        {
            image = new Image(resouceHandler.getClass().getResourceAsStream(
                    FilePaths.getImgDirectory() + "directory.png"
            ));
        }
        else
        {
            String extension = FileOperation.getFileExtension(file);

            String path = FilePaths.getImgDirectory() + extension + ".png";

            if (resouceHandler.getClass().getResourceAsStream(path) != null)
            {
                image = new Image(resouceHandler.getClass().getResourceAsStream(path));
            }
            else
            {
                image = new Image(resouceHandler.getClass().getResourceAsStream(
                        FilePaths.getImgDirectory() + "notfoundtype.png"
                ));
            }
        }
        
        return image;
    }
    
    // TODO: Esto esta mal, debe devolver lo leido en la file. Me lie por falta de tiempo
    public static String getStyleFile(String fileExtension)
    {
        String langStyleFile = FilePaths.getStyleDirectory() + "default.css";
        
        try
        {
            langStyleFile = FilePaths.getStyleDirectory() + fileExtension + ".css";
        }
        catch (Exception e)
        {
            System.err.println("This extension has no style");
        }
        
        return langStyleFile;
    }
}
