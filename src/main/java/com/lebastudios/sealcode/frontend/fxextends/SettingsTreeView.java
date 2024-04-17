package com.lebastudios.sealcode.frontend.fxextends;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class SettingsTreeView extends TreeView<String>
{
    public SettingsTreeView()
    {
        super(createRootNode());
        
        this.getRoot().setExpanded(true);
    }
    
    private static TreeItem<String> createRootNode()
    {
        TreeItem<String> root = new TreeItem<>("Settings", new IconView("settings.png"));
        
        root.getChildren().add(createMenuApariencia());
        root.getChildren().add(createMenuEditor());
        
        return root;
    }
    
    private static TreeItem<String> createMenuEditor()
    {
        TreeItem<String> root = new TreeItem<>("Editor");
        
        root.getChildren().add(new TreeItem<>("General"));
        root.getChildren().add(new TreeItem<>("Preferencias"));
        
        return root;
    }
    
    private static TreeItem<String> createMenuApariencia()
    {
        TreeItem<String> root = new TreeItem<>("Apariencia");

        root.getChildren().add(new TreeItem<>("Tema"));
        root.getChildren().add(new TreeItem<>("Fuente"));
        
        return root;
    }
}
