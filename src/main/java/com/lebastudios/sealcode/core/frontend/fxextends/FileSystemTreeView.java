package com.lebastudios.sealcode.core.frontend.fxextends;

import com.lebastudios.sealcode.util.FileOperation;
import com.lebastudios.sealcode.config.GlobalConfig;
import com.lebastudios.sealcode.config.Session;
import com.lebastudios.sealcode.core.os.OperativeSystem;
import javafx.scene.control.TreeView;

import java.io.File;

public final class FileSystemTreeView extends TreeView<String>
{
    public FileSystemTreeView()
    {
        super();

        this.setCellFactory(param -> new FileSystemTreeCell());

        openLastProjectDirectory();
    }

    public static FileSystemTreeItem createTreeView(File file)
    {
        if (file == null)
        {
            throw new IllegalArgumentException("File is null");
        }

        if (!file.exists())
        {
            throw new IllegalArgumentException("File does not exist");
        }

        FileSystemTreeItem root = new FileSystemTreeItem(file);

        root.setExpanded(false);

        if (file.isDirectory())
        {
            for (File child : file.listFiles())
            {
                if (GlobalConfig.getStaticInstance().userPrefs.ignoreGitDir && child.getName().equals(".git")) continue;

                root.getChildren().add(createTreeView(child));
            }
        }

        return root;
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

        this.getRoot().setExpanded(true);
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

    public void openNewProjectDirectory()
    {
        File file = FileOperation.directoryChooser().showDialog(null);

        if (file == null)
        {
            return;
        }

        openProyectDirectory(file);
    }

    public FileSystemTreeItem getTreeItemByFile(File file)
    {
        if (!file.exists())
        {
            return null;
        }

        String path = file.getAbsolutePath();
        String proyectDirectory = Session.getStaticInstance().proyectDirectory;

        if (!path.startsWith(proyectDirectory))
        {
            return null;
        }

        path = path.substring(proyectDirectory.length());

        if (path.startsWith(File.separator))
        {
            path = path.substring(1);
        }
        
        String[] pathParts = path.split(OperativeSystem.fileSeparator());

        var current = this.getRoot();
        
        for (String part : pathParts)
        {
            boolean found = false;

            for (var child : current.getChildren())
            {
                if (child.getValue().equals(part))
                {
                    current = child;
                    found = true;
                    break;
                }
            }

            if (!found)
            {
                return null;
            }
        }
        
        return (FileSystemTreeItem) current;
    }
}
