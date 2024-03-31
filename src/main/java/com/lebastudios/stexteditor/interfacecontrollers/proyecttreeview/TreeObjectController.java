package com.lebastudios.stexteditor.interfacecontrollers.proyecttreeview;

import com.lebastudios.stexteditor.app.FileOperation;
import com.lebastudios.stexteditor.interfacecontrollers.TabPaneController;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.io.File;

public class TreeObjectController
{
    private static final String IMAGE_PATH = "/img/";

    private File representingFile;
    private Image image;
    
    private long lastClickTime = 0;

    public TreeObjectController(File file)
    {
        super();

        representingFile = file;

        if (file.isDirectory())
        {
            image = new Image(getClass().getResourceAsStream(IMAGE_PATH + "directory.png"));
        }
        else
        {
            String extension = FileOperation.getFileExtension(file);

            String path = IMAGE_PATH + extension + ".png";

            if (getClass().getResourceAsStream(path) != null)
            {
                image = new Image(getClass().getResourceAsStream(path));
            }
            else
            {
                image = new Image(getClass().getResourceAsStream(IMAGE_PATH + "notfoundtype.png"));
            }
        }
    }

    public String getName()
    {
        return representingFile.getName();
    }

    public Image getImage()
    {
        return image;
    }

    @Override
    public String toString()
    {
        return representingFile.getName();
    }

    public void onMouseClicked(MouseEvent event)
    {
        if (representingFile.isDirectory()) 
        {
            return;
        }
        
        if (System.currentTimeMillis() - lastClickTime < 500)
        {
            lastClickTime = 0;

            TabPaneController.getInstance().openFile(representingFile);
            
            return;
        }
        
        lastClickTime = System.currentTimeMillis();
    }
}
