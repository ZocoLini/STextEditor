package com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.treeview;

import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.Linked2MM;
import com.lebastudios.stexteditor.applogic.FileOperation;
import com.lebastudios.stexteditor.applogic.config.global.Session;
import com.lebastudios.stexteditor.iobjects.fxextends.ProyectTreeCell;
import com.lebastudios.stexteditor.iobjects.fxextends.ProyectTreeCellContent;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.MainManager;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.SingletonManager;
import javafx.application.Platform;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.Executors;

public class ProyectTreeViewManager extends SingletonManager<TreeView<ProyectTreeCellContent>>
{
    private static ProyectTreeViewManager instance;

    public static ProyectTreeViewManager getInstance()
    {
        if (instance == null)
        {
            instance = new ProyectTreeViewManager();
        }

        return instance;
    }

    private WatchService watchService;

    private ProyectTreeViewManager()
    {
        super(MainManager.getInstance().proyectFileTreeView);

        openLastProjectDirectory();
    }

    private void openProyectDirectory(File file)
    {
        file = file.getAbsoluteFile();

        if (file.getPath().isEmpty())
        {
            return;
        }

        Session.getStaticInstance().proyectDirectory = file.getPath();

        managedObject.setCellFactory(param -> new ProyectTreeCell());
        managedObject.setRoot(createTreeView(file));

        managedObject.getRoot().setExpanded(true);
    }

    private void openLastProjectDirectory()
    {
        File file = new File(Session.getStaticInstance().proyectDirectory);

        if (!file.exists() || !file.isDirectory())
        {
            return;
        }

        openProyectDirectory(file);
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

    private static TreeItem<ProyectTreeCellContent> createTreeView(File file)
    {
        if (file == null)
        {
            throw new IllegalArgumentException("File is null");
        }

        if (!file.exists())
        {
            throw new IllegalArgumentException("File does not exist");
        }

        TreeItem<ProyectTreeCellContent> root = new TreeItem<>(new ProyectTreeCellContent(file));
        root.setExpanded(false);

        if (file.isDirectory())
        {
            for (File child : file.listFiles())
            {
                if (!child.getName().equals(".seal") && child.getName().charAt(0) == '.') continue;

                System.out.println(child.getName());
                root.getChildren().add(createTreeView(child));
            }
        }

        return root;
    }

    @Override
    public void loadChilds()
    {

    }
}
