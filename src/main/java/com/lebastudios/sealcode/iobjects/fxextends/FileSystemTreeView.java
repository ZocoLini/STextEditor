package com.lebastudios.sealcode.iobjects.fxextends;

import com.lebastudios.sealcode.applogic.FileOperation;
import com.lebastudios.sealcode.applogic.config.Session;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;

public final class FileSystemTreeView extends TreeView<ProyectTreeCellContent>
{
    public FileSystemTreeView()
    {
        super();

        this.setCellFactory(param -> new ProyectTreeCell());

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

        this.setRoot(createTreeView(file));
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

    public void openNewProjectDirectory()
    {
        File file = FileOperation.directoryChooser().showDialog(null);

        if (file == null)
        {
            return;
        }

        openProyectDirectory(file);
    }

    public static TreeItem<ProyectTreeCellContent> createTreeView(File file)
    {
        if (file == null)
        {
            throw new IllegalArgumentException("File is null");
        }

        if (!file.exists())
        {
            throw new IllegalArgumentException("File does not exist");
        }

        TreeItem<ProyectTreeCellContent> root = new ProyectTreeItem(new ProyectTreeCellContent(file));

        root.setExpanded(false);

        if (file.isDirectory())
        {
            for (File child : file.listFiles())
            {
                if (child.getName().equals(".git")) continue;

                root.getChildren().add(createTreeView(child));
            }
        }

        return root;
    }
}
