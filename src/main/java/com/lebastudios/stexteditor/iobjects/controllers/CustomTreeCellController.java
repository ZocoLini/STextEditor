package com.lebastudios.stexteditor.iobjects.controllers;

import com.lebastudios.stexteditor.applogic.FilePaths;
import com.lebastudios.stexteditor.applogic.Resources;
import com.lebastudios.stexteditor.iobjects.fxextends.CustomTreeCell;

import java.io.File;

public class CustomTreeCellController extends Controller
{
    private final CustomTreeCell treeCell;
    
    public CustomTreeCellController(CustomTreeCell treeCell)
    {
        super();

        this.treeCell = treeCell;
        
        instanciated = true;
    }

    @Override
    protected void onThemeChangue()
    {
        System.out.println(Resources.getClassStyleFromFile(FilePaths.getStyleDirectory() + "theme.css",
                "hola"));
    }
}
