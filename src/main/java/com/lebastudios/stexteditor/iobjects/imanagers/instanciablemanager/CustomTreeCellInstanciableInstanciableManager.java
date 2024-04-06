package com.lebastudios.stexteditor.iobjects.imanagers.instanciablemanager;

import com.lebastudios.stexteditor.exceptions.NotImplementedException;
import com.lebastudios.stexteditor.iobjects.fxextends.CustomTreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class CustomTreeCellInstanciableInstanciableManager extends InstanciableInstanciableManager<CustomTreeCell>
{
    public CustomTreeCellInstanciableInstanciableManager(CustomTreeCell treeCell)
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
        throw new NotImplementedException();
    }
    
    private void showDirectoryContextActions()
    {
        throw new NotImplementedException();
    }
}
