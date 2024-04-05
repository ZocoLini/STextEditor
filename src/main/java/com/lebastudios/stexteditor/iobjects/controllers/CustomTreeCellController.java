package com.lebastudios.stexteditor.iobjects.controllers;

import com.lebastudios.stexteditor.iobjects.fxextends.CustomTreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class CustomTreeCellController extends Controller<CustomTreeCell>
{
    public CustomTreeCellController(CustomTreeCell treeCell)
    {
        super(treeCell);
        
        instanciated = true;
    }

    @Override
    protected void addEventHandlers()
    {
        representingObject.addEventHandler(MouseEvent.MOUSE_CLICKED, this::showContextActions);
    }
    
    private void showContextActions(MouseEvent event)
    {
        if (event.getButton() != MouseButton.SECONDARY) return;

        System.out.println(123);
        
        if (representingObject.getRepresentingFile().isDirectory()) 
        {
            showDirectoryContextActions();
        }
        else
        {
            showFileContextActions();
        }
    }
    
    private void showFileContextActions()
    {
        // TODO
    }
    
    private void showDirectoryContextActions()
    {
        // TODO
    }
}
