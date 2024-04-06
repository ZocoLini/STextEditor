package com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.treeview;

import com.lebastudios.stexteditor.annotations.Linked2MM;
import com.lebastudios.stexteditor.applogic.FileOperation;
import com.lebastudios.stexteditor.applogic.config.Session;
import com.lebastudios.stexteditor.iobjects.fxextends.CustomTreeCell;
import com.lebastudios.stexteditor.iobjects.fxextends.CustomTreeCellContent;
import com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.MainSingletonManager;
import com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.SingletonManager;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;

public class TreeViewSingletonManager extends SingletonManager<TreeView<CustomTreeCellContent>>
{
    private static TreeViewSingletonManager instance;

    public static TreeViewSingletonManager getInstance()
    {
        if (instance == null)
        {
            instance = new TreeViewSingletonManager();
        }

        return instance;
    }

    private TreeViewSingletonManager()
    {
        super(MainSingletonManager.getInstance().treeView);
        
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

    public void openLastProjectDirectory()
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

    @Override
    public void loadChilds()
    {
        
    }
}
