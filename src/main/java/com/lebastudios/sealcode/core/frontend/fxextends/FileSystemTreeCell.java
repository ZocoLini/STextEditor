package com.lebastudios.sealcode.core.frontend.fxextends;

import com.lebastudios.sealcode.core.controllers.MainStageController;
import com.lebastudios.sealcode.core.frontend.dialogs.Dialogs;
import com.lebastudios.sealcode.core.logic.Indexer;
import com.lebastudios.sealcode.global.MessageType;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class FileSystemTreeCell extends TreeCell<String>
{
    private CellContextMenu cellContextMenu;

    public FileSystemTreeCell()
    {
        this.setOnContextMenuRequested(this::showContextActions);
        this.setOnMouseClicked(this::openRepresentingFile);
    }

    private void showContextActions(ContextMenuEvent event)
    {
        if (this.getTreeItem() == null) return;

        cellContextMenu.show(this, event.getScreenX(), event.getScreenY());
    }

    private void openRepresentingFile(MouseEvent event)
    {
        if (this.getTreeItem() == null) return;

        if (event.getClickCount() < 2) return;

        if (getRepresentingFile().isDirectory()) return;

        MainStageController.getInstance().openFile((FileSystemTreeItem) this.getTreeItem());
    }

    public File getRepresentingFile()
    {
        return ((FileSystemTreeItem) this.getTreeItem()).getRepresentingFile();
    }

    @Override
    protected void updateItem(String item, boolean empty)
    {
        super.updateItem(item, empty);

        if (empty || item == null)
        {
            setText(null);
            setGraphic(null);
        } else
        {
            cellContextMenu = new CellContextMenu(this);

            setText(item);
            setGraphic(this.getTreeItem().getGraphic());
        }
    }

    private class CellContextMenu extends ContextMenu
    {
        private final FileSystemTreeCell treeCell;
        
        public CellContextMenu(FileSystemTreeCell treeCell)
        {
            super();
            
            this.treeCell = treeCell;
            
            this.getItems().clear();
            this.getItems().addAll(customizeContextMenu(treeCell));
        }

        private List<MenuItem> customizeContextMenu(FileSystemTreeCell treeCell)
        {
            if (getRepresentingFile().isDirectory())
            {
                if (treeCell.getParent() == null)
                {
                    return makeRootContextActions();
                }

                return makeDirectoryContextActions();
            } 
            
            return makeFileContextActions();
        }

        private List<MenuItem> defaultMenuItems()
        {
            List<MenuItem> menuItems = new ArrayList<>();

            MenuItem menuItem = new MenuItem("Remove");
            menuItem.setOnAction(this::removeRepresentingFile);
            menuItems.add(menuItem);

            menuItem = new MenuItem("Rename");
            menuItem.setOnAction(this::renameRepresentingFile);
            menuItems.add(menuItem);

            return menuItems;
        }

        private List<MenuItem> makeFileContextActions()
        {
            return defaultMenuItems();
        }

        private List<MenuItem> makeRootContextActions()
        {
            List<MenuItem> menuItems = new ArrayList<>();

            MenuItem menuItem = new MenuItem("New File");
            menuItem.setOnAction(this::createFile);
            menuItems.add(menuItem);

            menuItem = new MenuItem("New Directory");
            menuItem.setOnAction(this::createDirectory);
            menuItems.add(menuItem);

            return menuItems;
        }

        private List<MenuItem> makeDirectoryContextActions()
        {
            List<MenuItem> menuItems = defaultMenuItems();

            MenuItem menuItem = new MenuItem("New File");
            menuItem.setOnAction(this::createFile);
            menuItems.add(menuItem);

            menuItem = new MenuItem("New Directory");
            menuItem.setOnAction(this::createDirectory);
            menuItems.add(menuItem);

            return menuItems;
        }

        private void renameRepresentingFile(ActionEvent event)
        {
            System.out.println("Se intentará renombrar " + getRepresentingFile());
            String nuevoNombre = Dialogs.insertTextDialog("Insert new styleClass", treeCell.getItem());

            if (nuevoNombre.isEmpty()) return;

            File parent = getRepresentingFile().getParentFile();
            File newFilePath = new File(parent.toString() + "/" + nuevoNombre);

            if (getRepresentingFile().renameTo(newFilePath))
            {
                treeCell.setItem(nuevoNombre);
                treeCell.getTreeItem().setValue(treeCell.getItem());
            } else
            {
                System.err.println("Error al instentar modificar el nombre.");
            }
        }

        private void createFile(ActionEvent event)
        {
            String name = Dialogs.insertTextDialog("Insert new file styleClass");

            File newFile = new File(getRepresentingFile().getAbsoluteFile() + "/" + name);

            try
            {
                if (newFile.createNewFile())
                {
                    treeCell.getTreeItem().getChildren().add(new FileSystemTreeItem(newFile));
                    Indexer.getIndexer().index(newFile);
                }
            } catch (IOException e)
            {
                MainStageController.getInstance().addNotification(
                        new Notification("Error creating file " + newFile.getName(), MessageType.Error)
                );
            }
        }

        private void createDirectory(ActionEvent event)
        {
            String name = Dialogs.insertTextDialog("Insert new directory styleClass");

            File newFile = new File(getRepresentingFile().getAbsoluteFile() + "/" + name);

            try
            {
                if (newFile.mkdirs())
                {
                    treeCell.getTreeItem().getChildren().add(new FileSystemTreeItem(newFile));
                }
            } catch (Exception e)
            {
                MainStageController.getInstance().addNotification(
                        new Notification("Error creating directory " + newFile.getName(), MessageType.Error)
                );
            }
        }

        private void removeRepresentingFile(ActionEvent event)
        {
            if (removeFile(getRepresentingFile()))
            {
                treeCell.getTreeItem().getParent().getChildren().remove(treeCell.getTreeItem());
                Indexer.getIndexer().unIndex(getRepresentingFile());
            }

        }

        private boolean removeFile(File fileToRemove)
        {
            if (!fileToRemove.exists())
            {
                System.out.println("File " + fileToRemove + " does not exists.");
                return false;
            }

            if (!fileToRemove.isFile())
            {
                for (var file : fileToRemove.listFiles())
                {
                    removeFile(file);
                }
            }

            try
            {
                return fileToRemove.delete();
            } catch (Exception e)
            {
                MainStageController.getInstance().addNotification(
                        new Notification("Error removing directory " + fileToRemove.getName() + ". An update is" +
                                " recomended in te proyect tree view.", MessageType.Error)
                );
            }
            
            return false;
        }
    }
}
