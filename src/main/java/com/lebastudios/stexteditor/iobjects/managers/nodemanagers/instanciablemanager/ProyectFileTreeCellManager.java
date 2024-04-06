package com.lebastudios.stexteditor.iobjects.managers.nodemanagers.instanciablemanager;

import com.lebastudios.stexteditor.iobjects.fxextends.ProyectFileTreeCell;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;

public class ProyectFileTreeCellManager extends InstanciableManager<ProyectFileTreeCell>
{
    public ProyectFileTreeCellManager(ProyectFileTreeCell treeCell)
    {
        super(treeCell);
        
        instanciated = true;
    }

    @Override
    protected void addEventHandlers()
    {
        managedObject.setOnContextMenuRequested(this::showContextActions);
    }
    
    private void showContextActions(ContextMenuEvent event)
    {
        if (managedObject.getRepresentingFile().isDirectory()) 
        {
            showDirectoryContextActions(event);
        }
        else
        {
            showFileContextActions(event);
        }
    }
    
    private void showFileContextActions(ContextMenuEvent event)
    {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem renameItem = new MenuItem("Not implemented");
        MenuItem deleteItem = new MenuItem("Not implemented");
        
        contextMenu.getItems().addAll(renameItem, deleteItem);
        
        contextMenu.show(managedObject, event.getScreenX(), event.getScreenY());
    }
    
    private void showDirectoryContextActions(ContextMenuEvent event)
    {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem renameItem = new MenuItem("Not implemented");
        MenuItem deleteItem = new MenuItem("Not implemented");

        contextMenu.getItems().addAll(renameItem, deleteItem);

        contextMenu.show(managedObject, event.getScreenX(), event.getScreenY());
    }
}
