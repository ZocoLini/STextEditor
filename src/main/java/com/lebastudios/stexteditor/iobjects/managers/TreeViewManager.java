package com.lebastudios.stexteditor.iobjects.managers;

import com.lebastudios.stexteditor.annotations.Linked2MM;
import com.lebastudios.stexteditor.applogic.FileOperation;
import com.lebastudios.stexteditor.applogic.config.Session;
import com.lebastudios.stexteditor.applogic.config.Theme;
import com.lebastudios.stexteditor.iobjects.fxextends.CustomTreeCell;
import com.lebastudios.stexteditor.iobjects.fxextends.CustomTreeCellContent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;

public class TreeViewManager extends Manager<TreeView<CustomTreeCellContent>>
{
    private static TreeViewManager instance;

    public static TreeViewManager getInstance()
    {
        if (instance == null)
        {
            instance = new TreeViewManager();
        }

        return instance;
    }

    private TreeViewManager()
    {
        super(MainManager.getInstance().treeView);
        
        instanciated = true;
    }
    
    private void openProyectDirectory(File file)
    {
        file = file.getAbsoluteFile();

        if (file.getPath().isEmpty())
        {
            return;
        }

        Session.getStaticInstance().proyectDirectory = file.getPath();

        representingObject.setCellFactory(param -> new CustomTreeCell());
        representingObject.setRoot(createTreeView(file));
        
        representingObject.getRoot().setExpanded(true);
    }

    void openLastProjectDirectory()
    {
        File file = new File(Session.getStaticInstance().proyectDirectory);

        if (!file.exists() || !file.isDirectory())
        {
            return;
        }

        openProyectDirectory(file);
    }

    private static TreeItem<CustomTreeCellContent> createTreeView(File file)
    {
        if (file == null)
        {
            throw new IllegalArgumentException("File is null");
        }

        if (!file.exists())
        {
            throw new IllegalArgumentException("File does not exist");
        }

        TreeItem<CustomTreeCellContent> root = new TreeItem<>(new CustomTreeCellContent(file));
        root.setExpanded(false);

        if (file.isDirectory())
        {
            for (File child : file.listFiles())
            {
                if (child.getName().charAt(0) == '.') continue;

                root.getChildren().add(createTreeView(child));
            }
        }

        return root;
    }

    @Linked2MM
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
