package com.lebastudios.sealcode.core.controllers.settingsPanels;

import com.lebastudios.sealcode.core.frontend.dialogs.Dialogs;
import com.lebastudios.sealcode.core.frontend.fxextends.IconTreeItem;
import com.lebastudios.sealcode.core.logic.config.FilePaths;
import com.lebastudios.sealcode.core.logic.fileobj.EquivalentExtensionsJSON;
import com.lebastudios.sealcode.core.logic.fileobj.JsonFile;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;

import java.io.File;

public class EquivalentExtensionsController
{
    @FXML
    private ListView<String> equivalentExtensionsListView;
    @FXML
    private TreeView<String> filtersFilesTreeView;

    private final JsonFile<EquivalentExtensionsJSON> equivalentExtensionsFile = new JsonFile<>(
            new File(FilePaths.getEquivalentExtensionsFile()),
            new EquivalentExtensionsJSON()
    );

    public void initialize()
    {
        loadTreeView();

        filtersFilesTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue == null) return;
            if (newValue.getParent() == null)
            {
                equivalentExtensionsListView.getItems().clear();
                return;
            }

            loadListView();
        });
    }

    private void loadTreeView()
    {
        filtersFilesTreeView.getRoot().getChildren().clear();
        
        for (var filter : equivalentExtensionsFile.getInstance().filter)
        {
            filtersFilesTreeView.getRoot().getChildren().add(new IconTreeItem<>(
                    filter.extension,
                    "ext_" + filter.extension + ".png"
            ));
        }

        filtersFilesTreeView.getRoot().setExpanded(true);
    }

    private void loadListView()
    {
        equivalentExtensionsListView.getItems().clear();

        String actualExtension = filtersFilesTreeView.getSelectionModel().getSelectedItem().getValue();

        equivalentExtensionsListView.getItems().addAll(
                equivalentExtensionsFile.getInstance().getEquivalentExtensionsFor(actualExtension)
        );
    }

    public void createNewFilter()
    {
        String nombre = Dialogs.insertTextDialog("New extension filter");
        equivalentExtensionsFile.getInstance().filter.add(new EquivalentExtensionsJSON.EquivalentExtensions(nombre));
        
        equivalentExtensionsFile.writeToFile();
        loadTreeView();
    }

    public void deleteFilter()
    {
        final var selectedItem = filtersFilesTreeView.getSelectionModel().getSelectedItem();
        
        if (selectedItem == null) return; 
        
        equivalentExtensionsFile.getInstance().filter.removeIf(eqExt -> eqExt.extension.equals(selectedItem.getValue()));

        equivalentExtensionsFile.writeToFile();
        loadTreeView();
    }

    public void addEquivalentExtension()
    {
        String nombre = Dialogs.insertTextDialog("New equivalent extension");

        final var selectedItem = filtersFilesTreeView.getSelectionModel().getSelectedItem();
        equivalentExtensionsFile.getInstance().getEquivalentExtensionsFor(selectedItem.getValue()).add(nombre);
        
        equivalentExtensionsFile.writeToFile();
        loadListView();
    }

    public void deleteEquivalentExtension()
    {
        final var selectedItem = filtersFilesTreeView.getSelectionModel().getSelectedItem();
        final var selectedEqExt = equivalentExtensionsListView.getSelectionModel().getSelectedItem();
        equivalentExtensionsFile.getInstance().getEquivalentExtensionsFor(selectedItem.getValue()).removeIf(value -> value.equals(selectedEqExt));
        
        equivalentExtensionsFile.writeToFile();
        loadListView();
    }
}
