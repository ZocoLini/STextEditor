package com.lebastudios.sealcode.controllers.settingsPanels;

import com.lebastudios.sealcode.frontend.Dialogs;
import com.lebastudios.sealcode.frontend.IconTreeItem;
import com.lebastudios.sealcode.FilePaths;
import com.lebastudios.sealcode.fileobj.JsonFile;
import com.lebastudios.sealcode.fileobj.LangCompletationsJSON;
import com.lebastudios.sealcode.FileOperation;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
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
        loadTreeView();
        
        keyWordsTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue == null) return;
            if (newValue.getParent() == null) return;

            actualCompletations = new JsonFile<>(
                    new File(FilePaths.getCompletationsDir() + newValue.getValue() + ".json"),
                    new LangCompletationsJSON()
            );

            loadListView();
        });
        
        keyWordsTreeView.getRoot().setExpanded(true);
    }

    private void loadTreeView()
    {
        File completationsFolder = new File(FilePaths.getCompletationsDir());

        keyWordsTreeView.getRoot().getChildren().clear();
        
        for (var file : completationsFolder.listFiles())
        {
            IconTreeItem<String> item = new IconTreeItem<>(
                    FileOperation.getFileName(file),
                    "ext_" + FileOperation.getFileName(file) + ".png"
            );

            keyWordsTreeView.getRoot().getChildren().add(item);
        }
    }
    
    private void loadListView()
    {
        keyWordsListView.getItems().clear();

        for (var keyWordCompletation : actualCompletations.get().keywordsCompletations)
        {
            keyWordsListView.getItems().add(keyWordCompletation.getValue());
        }
    }

    @FXML
    private void removeLanguage()
    {
        actualCompletations.delete();
        loadTreeView();
    }

    @FXML
    private void addLanguage()
    {
        String languageName = Dialogs.insertTextDialog("Insert language styleClass", "Insert the styleClass of the language");

        if (languageName == null || languageName.isEmpty() || languageName.isBlank()) return;

        JsonFile.createNewFile(FilePaths.getCompletationsDir(), languageName);

        loadTreeView();
    }

    @FXML
    private void addKeyword()
    {
        if (actualCompletations == null) return;

        String selectedItem = Dialogs.insertTextDialog("Insert keyword", "Insert the keyword to add");
        
        if (selectedItem == null || selectedItem.isEmpty() || selectedItem.isBlank()) return;
        
        actualCompletations.get().keywordsCompletations.add(new LangCompletationsJSON.KeyWordCompletation(selectedItem));
        
        actualCompletations.write();
        
        loadListView();
    }

    @FXML
    private void removeKeyword()
    {
        if (actualCompletations == null) return;

        String selectedItem = keyWordsListView.getSelectionModel().getSelectedItem();
        
        if (selectedItem == null) return;
        
        actualCompletations.get().keywordsCompletations.removeIf(variable -> variable.getValue().equals(selectedItem));
        
        actualCompletations.write();
        
        loadListView();
    }
}
