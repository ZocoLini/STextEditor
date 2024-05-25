package com.lebastudios.sealcode.core.controllers.settingsPanels;

import com.lebastudios.sealcode.core.frontend.dialogs.Dialogs;
import com.lebastudios.sealcode.core.frontend.fxextends.IconTreeItem;
import com.lebastudios.sealcode.core.logic.config.FilePaths;
import com.lebastudios.sealcode.core.logic.fileobj.JsonFile;
import com.lebastudios.sealcode.core.logic.fileobj.LangCompletationsJSON;
import com.lebastudios.sealcode.global.FileOperation;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;

public class KeywordCompletionsController
{
    @FXML
    public TreeView<String> keyWordsTreeView;
    @FXML
    public ListView<String> keyWordsListView;

    private JsonFile<LangCompletationsJSON> actualCompletations;
    
    public void initialize()
    {
        File completationsFolder = new File(FilePaths.getCompletationsDir());

        for (var file : completationsFolder.listFiles())
        {
            IconTreeItem<String> item = new IconTreeItem<>(
                    FileOperation.getFileName(file),
                    "ext_" + FileOperation.getFileName(file) + ".png"
            );

            keyWordsTreeView.getRoot().getChildren().add(item);
        }

        keyWordsTreeView.setOnMouseClicked(event ->
        {
            loadKeywords();
        });
        
        keyWordsTreeView.getRoot().setExpanded(true);
    }

    private void loadKeywords()
    {
        TreeItem<String> selectedItem = keyWordsTreeView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) return;
        if (selectedItem.getParent() == null) return;

        actualCompletations = new JsonFile<>(
                new File(FilePaths.getCompletationsDir() + selectedItem.getValue() + ".json"),
                new LangCompletationsJSON()
        );

        keyWordsListView.getItems().clear();

        for (var keyWordCompletation : actualCompletations.get().keywordsCompletations)
        {
            keyWordsListView.getItems().add(keyWordCompletation.getValue());
        }
    }

    @FXML
    private void removeLanguage()
    {
        TreeItem<String> selectedItem = keyWordsTreeView.getSelectionModel().getSelectedItem();

        if (selectedItem.getParent() == null) return;

        File file = new File(FilePaths.getCompletationsDir() + selectedItem.getValue() + ".json");

        if (file.exists())
        {
            file.delete();
        }

        keyWordsTreeView.getRoot().getChildren().remove(selectedItem);
    }

    @FXML
    private void addLanguage()
    {
        String languageName = Dialogs.insertTextDialog("Insert language styleClass", "Insert the styleClass of the language");

        if (languageName == null || languageName.isEmpty() || languageName.isBlank()) return;

        new JsonFile<>(
                new File(FilePaths.getCompletationsDir() + "default.json"),
                new LangCompletationsJSON()
        ).createNewFile(new File(FilePaths.getCompletationsDir()), languageName);

        IconTreeItem<String> item = new IconTreeItem<>(languageName, languageName + ".png");

        keyWordsTreeView.getRoot().getChildren().add(item);
    }

    @FXML
    private void addKeyword()
    {
        if (actualCompletations == null) return;

        String selectedItem = Dialogs.insertTextDialog("Insert keyword", "Insert the keyword to add");
        
        if (selectedItem == null || selectedItem.isEmpty() || selectedItem.isBlank()) return;
        
        actualCompletations.get().keywordsCompletations.add(new LangCompletationsJSON.KeyWordCompletation(selectedItem));
        
        actualCompletations.write();
        
        loadKeywords();
    }

    @FXML
    private void removeKeyword()
    {
        if (actualCompletations == null) return;

        String selectedItem = keyWordsListView.getSelectionModel().getSelectedItem();
        
        if (selectedItem == null) return;
        
        actualCompletations.get().keywordsCompletations.removeIf(variable -> variable.getValue().equals(selectedItem));
        
        actualCompletations.write();
        
        loadKeywords();
    }
}
