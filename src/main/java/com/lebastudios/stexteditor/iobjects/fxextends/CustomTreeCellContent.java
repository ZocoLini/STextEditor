package com.lebastudios.stexteditor.iobjects.fxextends;

import com.lebastudios.stexteditor.applogic.Resources;
import com.lebastudios.stexteditor.iobjects.managers.TabPaneManager;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.io.File;

public class CustomTreeCellContent
{
    private File representingFile;
    private Image image;
    
    private long lastClickTime = 0;

    public CustomTreeCellContent(File file)
    {
        super();

        representingFile = file;

        image = Resources.getImg(file);
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

            TabPaneManager.getInstance().openFile(representingFile);
            
            return;
        }
        
        lastClickTime = System.currentTimeMillis();
    }
}
