package com.lebastudios.stexteditor.iobjects.controllers;

import com.lebastudios.stexteditor.annotations.Linked2MC;
import com.lebastudios.stexteditor.applogic.FileOperation;
import com.lebastudios.stexteditor.applogic.config.Session;
import com.lebastudios.stexteditor.iobjects.CustomTreeCell;
import javafx.geometry.Insets;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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
    }
    
    private static final TreeView<TreeObjectController> treeView = MainController.getInstance().treeView;

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

    @Override
    protected void onThemeChangue()
    {
        // TODO: Hacer que funcione
        treeView.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        treeView.setStyle("-fx-background-color: red");
    }
}
