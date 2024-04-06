package com.lebastudios.stexteditor.iobjects.managers.nodemanagers.instanciablemanager;

import com.lebastudios.stexteditor.iobjects.fxextends.ProyectTreeCell;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.MainManager;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.tabpane.CodeTabPaneManager;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.treeview.ProyectTreeViewManager;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.io.File;

public class ProyectTreeCellManager extends InstanciableManager<ProyectTreeCell>
{
    public ProyectTreeCellManager(ProyectTreeCell treeCell)
    {
        super(treeCell);
    }

    public File getRepresentingFile() 
    {
        return managedObject.getItem().getRepresentingFile(); 
    }
    
    @Override
    protected void addEventHandlers()
    {
        managedObject.setOnContextMenuRequested(this::showContextActions);
        managedObject.setOnMouseClicked(this::openRepresentingFile);
    }
    
    public void openRepresentingFile(MouseEvent event)
    {
        if (event.getClickCount() < 2) return;
        
        if (getRepresentingFile().isDirectory()) return;

        CodeTabPaneManager.getInstance().openFile(getRepresentingFile());
    }
    
    private void showContextActions(ContextMenuEvent event)
    {
        if (getRepresentingFile().isDirectory()) 
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

    private void checkForContentUpdates() throws InterruptedException
    {
        Thread.sleep(1000);

        File representingFile = getRepresentingFile();
        
        if (!representingFile.isDirectory())
        {
            System.out.println("No se monitorea: " + getRepresentingFile().getPath());
            return;
        }
        
        while (true)
        {
            Thread.sleep(1000 / 15);

            System.out.println("Monitoreando: " + getRepresentingFile().getPath());
        }
            
    }
}
