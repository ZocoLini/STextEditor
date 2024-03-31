package com.lebastudios.stexteditor.interfacecontrollers.proyecttreeview;

import com.lebastudios.stexteditor.app.Resources;
import com.lebastudios.stexteditor.interfacecontrollers.TabPaneController;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.io.File;

public class TreeObjectController
{
    private File representingFile;
    private Image image;
    
    private long lastClickTime = 0;

    public TreeObjectController(File file)
    {
        super();

        representingFile = file;

        image = Resources.getImg(file, this);
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
