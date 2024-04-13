package com.lebastudios.stexteditor.iobjects.fxextends;

import com.lebastudios.stexteditor.exceptions.NotImplementedException;
import com.lebastudios.stexteditor.iobjects.AlertsInstanciator;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.tabpane.CodeTabPaneManager;

import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProyectTreeCell extends TreeCell<ProyectTreeCellContent>
{
    private ContextMenu contextMenu = new ContextMenu(defaultMenuItems());

    private MenuItem[] defaultMenuItems()
    {
        List<MenuItem> menuItems = new ArrayList<>();

        MenuItem menuItem = new MenuItem("Remove");
        menuItem.setOnAction(this::removeRepresentingFile);
        menuItems.add(menuItem);

        menuItem = new MenuItem("Rename");
        menuItem.setOnAction(this::renameRepresentingFile);
        menuItems.add(menuItem);

        MenuItem[] arrayOfMenuItems = new MenuItem[0];
        return menuItems.toArray(arrayOfMenuItems);
    }
    
    private final ImageView imageView;
    
    public ProyectTreeCell() {
        imageView = new ImageView();
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);

        addEventHandlers();
    }

    protected void addEventHandlers()
    {
        this.setOnContextMenuRequested(this::showContextActions);
        this.setOnMouseClicked(this::openRepresentingFile);
    }

    public File getRepresentingFile()
    {
        return this.getItem().getRepresentingFile();
    }

    private void renameRepresentingFile(ActionEvent event)
    {
        if (true) 
        {
            throw new NotImplementedException("When a file renames his name, his childs doesnt. Thining to implement " +
                    "Paths");
        }
        
        System.out.println("Se intentará renombrar " + getRepresentingFile().getAbsolutePath());
        String nuevoNombre = AlertsInstanciator.insertTextDialog("Insert new name", "something");

        File parent = getRepresentingFile().getParentFile();
        File newFilePath = new File(parent.toString() + "/" + nuevoNombre);

        if (getRepresentingFile().renameTo(newFilePath))
        {
            this.setItem(new ProyectTreeCellContent(newFilePath));
            this.getTreeItem().setValue(this.getItem());
        }
        else
        {
            System.err.println("Error al instentar modificar el nombre.");
        }
    }
    
    private void createFile(ActionEvent event)
    {
        String name = "new File.txt";
        
        File newFile = new File(getRepresentingFile().getAbsoluteFile() + "/" + name);

        try
        {
            newFile.createNewFile();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    private void createDirectory(ActionEvent event)
    {
        String name = "new directory";

        File newFile = new File(getRepresentingFile().getAbsoluteFile() + "/" + name);

        newFile.mkdirs();
    }
    
    private void removeRepresentingFile(ActionEvent event)
    {
        System.out.println("Se intentará eliminar " + getRepresentingFile().getAbsolutePath());
        removeFile(getRepresentingFile());
    }

    private void removeFile(File fileToRemove)
    {
        if (!fileToRemove.exists())
        {
            System.out.println("File " + fileToRemove + " does not exists.");
            return;
        }

        if (fileToRemove.isFile())
        {
            fileToRemove.delete();
            return;
        }

        for (var file : fileToRemove.listFiles())
        {
            removeFile(file);
        }

        fileToRemove.delete();
    }

    private void openRepresentingFile(MouseEvent event)
    {
        if (event.getClickCount() < 2) return;

        if (getRepresentingFile().isDirectory()) return;

        CodeTabPaneManager.getInstance().openFile(getRepresentingFile());
    }

    private void showContextActions(ContextMenuEvent event)
    {
        contextMenu.show(this, event.getScreenX(), event.getScreenY());
    }

    private void customizeContextMenu()
    {
        if (getRepresentingFile().isDirectory())
        {
            if (this.getTreeItem().equals(this.getTreeView().getRoot())) 
            {
                makeRootContextActions();
                return;
            }
            
            makeDirectoryContextActions();
        }
        else
        {
            makeFileContextActions();
        }
    }

    private void makeFileContextActions()
    {
        MenuItem renameItem = new MenuItem("Es una file");

        contextMenu = new ContextMenu(defaultMenuItems());
        
        contextMenu.getItems().add(renameItem);
    }

    private void makeRootContextActions()
    {
        
    }
    
    private void makeDirectoryContextActions()
    {
        contextMenu = new ContextMenu(defaultMenuItems());
        
        MenuItem menuItem = new MenuItem("New File");
        menuItem.setOnAction(this::createFile);
        contextMenu.getItems().add(menuItem);
        
        menuItem = new MenuItem("New Directory");
        menuItem.setOnAction(this::createDirectory);
        contextMenu.getItems().add(menuItem);
    }
    
    @Override
    protected void updateItem(ProyectTreeCellContent item, boolean empty) 
    {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            customizeContextMenu();
            this.setItem(item);
            
            setText(item.getName());
            imageView.setImage(item.getImage());
            setGraphic(imageView);
        }
    }
}
