package com.lebastudios.stexteditor.iobjects.controllers;

import com.lebastudios.stexteditor.applogic.Resources;
import com.lebastudios.stexteditor.iobjects.fxextends.CustomTreeCell;

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
        treeCell.setStyle(Resources.getThemeClassStyleFromFile("secondary-bg"));
    }
}
