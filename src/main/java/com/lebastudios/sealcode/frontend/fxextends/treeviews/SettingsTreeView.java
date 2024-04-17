package com.lebastudios.sealcode.frontend.fxextends.treeviews;

import com.lebastudios.sealcode.frontend.fxextends.IconView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class SettingsTreeView extends TreeView<String>
{
    public SettingsTreeView()
    {
        super(createRootNode());

        this.setCellFactory(param -> new SettingsTreeCell());

        this.getRoot().setExpanded(true);
    }

    private static TreeItem<String> createRootNode()
    {
        TreeItem<String> root = new TreeItem<>("Settings", new IconView("settings.png"));

        root.getChildren().add(createMenuApariencia());
        root.getChildren().add(createMenuEditor());
        root.getChildren().add(createMenuProyecto());

        return root;
    }

    private static TreeItem<String> createMenuEditor()
    {
        TreeItem<String> root = new TreeItem<>("Editor", new IconView("c.png"));

        root.getChildren().add(new SettingsTreeItem("General", 
                "generalAnchorPane.fxml", 
                new IconView("hombreQuieto.png")));
        root.getChildren().add(new SettingsTreeItem("Preferencias", "preferenciasAnchorPane.fxml", 
                new IconView("preferencias.png")));

        return root;
    }

    private static TreeItem<String> createMenuApariencia()
    {
        TreeItem<String> root = new TreeItem<>("Apariencia", new IconView("gafasSol.png"));

        root.getChildren().add(new SettingsTreeItem("Tema", "themeAnchorPane.fxml",
                new IconView("pincel.png")));
        root.getChildren().add(new SettingsTreeItem("Fuente", "fuenteAnchorPane.fxml",
                new IconView("fuente.png")));

        return root;
    }

    private static TreeItem<String> createMenuProyecto()
    {
        TreeItem<String> root = new TreeItem<>("Proyecto", new IconView("fileSystem.png"));

        root.getChildren().add(new SettingsTreeItem("Inspecciones", "inspeccionesAnchorPane.fxml",
                new IconView("lupa.png")));

        return root;
    }
}
