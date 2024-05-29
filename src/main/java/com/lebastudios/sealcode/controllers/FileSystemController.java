package com.lebastudios.sealcode.controllers;

import com.lebastudios.sealcode.frontend.FileSystemTreeItem;
import com.lebastudios.sealcode.frontend.FileSystemTreeView;
import com.lebastudios.sealcode.events.AppEvents;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;

import java.io.File;

public class FileSystemController
{
    private static FileSystemController instance;
    
    @FXML
    private FileSystemTreeView fileSystemTreeView;

    public static FileSystemController getInstance()
    {
        return instance;
    }

    public FileSystemController()
    {
        instance = this;

        AppEvents.onGlobalConfigUpdate.addListener(this::reloadFileSystem);
    }

    @FXML
    public void reloadFileSystem()
    {
        fileSystemTreeView.openLastProjectDirectory();
    }

    @FXML
    public void collapseTree()
    {
        collapseItem(fileSystemTreeView.getRoot());
    }

    private void collapseItem(TreeItem<?> item)
    {
        item.setExpanded(false);
        item.getChildren().forEach(this::collapseItem);
    }
    
    @FXML
    public void expandTree()
    {
        expandItem(fileSystemTreeView.getRoot());
    }
    
    public void openNewProjectDirectory()
    {
        fileSystemTreeView.openNewProjectDirectory();
    }
    
    public FileSystemTreeItem getTreeItemByFile(File file)
    {
        return fileSystemTreeView.getTreeItemByFile(file);
    }
    
    public void expandItem(TreeItem<?> item)
    {
        item.setExpanded(true);
        item.getChildren().forEach(this::expandItem);
    }
}
