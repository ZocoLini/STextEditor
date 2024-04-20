package com.lebastudios.sealcode.controllers;

import com.lebastudios.sealcode.events.AppEvents;
import com.lebastudios.sealcode.frontend.fxextends.treeviews.FileSystemTreeView;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;

public class FileSystemController
{
    private static FileSystemController instance;
    
    @FXML
    public FileSystemTreeView fileSystemTreeView;

    public static FileSystemController getInstance()
    {
        return instance;
    }

    public FileSystemController()
    {
        instance = this;

        AppEvents.onPreferencesUpdate.addListener(this::reloadFileSystem);
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
    
    public void expandItem(TreeItem<?> item)
    {
        item.setExpanded(true);
        item.getChildren().forEach(this::expandItem);
    }
}
