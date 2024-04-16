package com.lebastudios.sealcode.iobjects.fxextends;

import com.lebastudios.sealcode.applogic.Resources;
import javafx.scene.image.Image;

import java.io.File;

public class ProyectTreeCellContent
{
    private final File representingFile;
    private final Image image;

    public ProyectTreeCellContent(File file)
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

    public File getRepresentingFile() {
        return representingFile;
    }
}
