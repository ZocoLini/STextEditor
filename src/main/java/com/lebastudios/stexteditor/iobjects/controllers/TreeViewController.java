package com.lebastudios.stexteditor.iobjects.controllers;

import com.lebastudios.stexteditor.annotations.Linked2MC;
import com.lebastudios.stexteditor.applogic.FileOperation;
import com.lebastudios.stexteditor.applogic.config.Session;
import com.lebastudios.stexteditor.iobjects.CustomTreeCell;
import javafx.scene.control.TreeView;

import java.io.File;

public class TreeViewController extends Controller
{
    private static TreeViewController instance;

    public static TreeViewController getInstance()
    {
        if (instance == null)
        {
            instance = new TreeViewController();
        }

        return instance;
    }

    private TreeViewController()
    {
        super();
        
        this.treeView = MainController.getInstance().treeView;
    }
    
    private final TreeView<TreeObjectController> treeView;

    private void openProyectDirectory(File file)
    {
        file = file.getAbsoluteFile();

        if (file.getPath().isEmpty())
        {
            return;
        }

        Session.getStaticInstance().proyectDirectory = file.getPath();

        treeView.setCellFactory(param -> new CustomTreeCell());
        treeView.setRoot(FileOperation.createTreeView(file));
        
        treeView.getRoot().setExpanded(true);
    }

    void openLastProjectDirectory()
    {
        File file = new File(Session.getStaticInstance().proyectDirectory);

        if (!file.exists() || !file.isDirectory())
        {
            System.out.println(13);
            return;
        }

        openProyectDirectory(file);
    }

    @Linked2MC
    public void openNewProjectDirectory()
    {
        File file = FileOperation.directoryChooser().showDialog(null);

        if (file == null)
        {
            return;
        }

        openProyectDirectory(file);
    }
}
