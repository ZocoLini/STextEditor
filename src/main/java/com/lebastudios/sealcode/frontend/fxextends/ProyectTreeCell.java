package com.lebastudios.sealcode.frontend.fxextends;

import com.lebastudios.sealcode.frontend.Dialogs;
import com.lebastudios.sealcode.frontend.stages.main.MainStageController;
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

public final class ProyectTreeCell extends TreeCell<ProyectTreeCellContent>
{
    private final ImageView imageView;
    private ContextMenu contextMenu = new ContextMenu(defaultMenuItems());

    public ProyectTreeCell()
    {
        imageView = new ImageView();
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        addEventHandlers();
    }

    private void addEventHandlers()
    {
        this.setOnContextMenuRequested(this::showContextActions);
        this.setOnMouseClicked(this::openRepresentingFile);
    }

    private void showContextActions(ContextMenuEvent event)
    {
        contextMenu.show(this, event.getScreenX(), event.getScreenY());
    }

    private void openRepresentingFile(MouseEvent event)
    {
        if (event.getClickCount() < 2) return;

        if (getRepresentingFile().isDirectory()) return;

        MainStageController.getInstance().openFile(getRepresentingFile());
    }

    public File getRepresentingFile()
    {
        return this.getItem().getRepresentingFile();
    }

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

    //TODO: When a file renames his name, his childs doesnt update their path
    private void renameRepresentingFile(ActionEvent event)
    {
        System.out.println("Se intentará renombrar " + getRepresentingFile().getAbsolutePath());
        String nuevoNombre = Dialogs.insertTextDialog("Insert new name");

        if (nuevoNombre.isEmpty()) return;

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
        String name = Dialogs.insertTextDialog("Insert new file name");

        File newFile = new File(getRepresentingFile().getAbsoluteFile() + "/" + name);

        try
        {
            newFile.createNewFile();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        updateThisItemChildren();
    }

    private void createDirectory(ActionEvent event)
    {
        String name = Dialogs.insertTextDialog("Insert new directory name");

        File newFile = new File(getRepresentingFile().getAbsoluteFile() + "/" + name);

        newFile.mkdirs();
        updateThisItemChildren();
    }

    private void removeRepresentingFile(ActionEvent event)
    {
        System.out.println("Se intentará eliminar " + getRepresentingFile().getAbsolutePath());
        removeFile(getRepresentingFile());

        updateParentItemChildren();
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

    private void updateParentItemChildren()
    {
        ((ProyectTreeItem) this.getTreeItem().getParent()).actualizarHijos();
    }

    private void updateThisItemChildren()
    {
        ((ProyectTreeItem) this.getTreeItem()).actualizarHijos();
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
        contextMenu = new ContextMenu(defaultMenuItems());
    }

    private void makeRootContextActions()
    {
        contextMenu = new ContextMenu();

        MenuItem menuItem = new MenuItem("New File");
        menuItem.setOnAction(this::createFile);
        contextMenu.getItems().add(menuItem);

        menuItem = new MenuItem("New Directory");
        menuItem.setOnAction(this::createDirectory);
        contextMenu.getItems().add(menuItem);
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

        if (empty || item == null)
        {
            setText(null);
            setGraphic(null);
        }
        else
        {
            customizeContextMenu();
            this.setItem(item);

            setText(item.getName());
            imageView.setImage(item.getImage());
            setGraphic(imageView);
        }
    }
}
