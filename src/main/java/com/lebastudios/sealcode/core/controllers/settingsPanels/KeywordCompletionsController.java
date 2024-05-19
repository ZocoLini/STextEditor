package com.lebastudios.sealcode.core.controllers.settingsPanels;

import com.lebastudios.sealcode.core.frontend.dialogs.Dialogs;
import com.lebastudios.sealcode.core.frontend.fxextends.IconTreeItem;
import com.lebastudios.sealcode.core.logic.config.FilePaths;
import com.lebastudios.sealcode.core.logic.defaultcompletations.KeyWordCompletation;
import com.lebastudios.sealcode.core.logic.defaultcompletations.LangCompletations;
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

    private LangCompletations actualCompletations;
    
    public void initialize()
    {
        File completationsFolder = new File(FilePaths.getProgLangCompletationsDirectory());

        for (var file : completationsFolder.listFiles())
        {
            IconTreeItem<String> item = new IconTreeItem<>(
                    FileOperation.getFileName(file),
                    FileOperation.getFileExtension(file) + ".png"
            );

            keyWordsTreeView.getRoot().getChildren().add(item);

            keyWordsTreeView.setOnMouseClicked(event ->
            {
                loadKeywords();
            });
        }

        keyWordsTreeView.getRoot().setExpanded(true);
    }

    private void loadKeywords()
    {
        TreeItem<String> selectedItem = keyWordsTreeView.getSelectionModel().getSelectedItem();

        if (selectedItem.getParent() == null) return;

        actualCompletations = LangCompletations.readCompletationFromFile(selectedItem.getValue());

        keyWordsListView.getItems().clear();

        for (var keyWordCompletation : actualCompletations.getKeywordsCompletations())
        {
            keyWordsListView.getItems().add(keyWordCompletation.getValue());
        }
    }

    public void removeLanguage()
    {
        TreeItem<String> selectedItem = keyWordsTreeView.getSelectionModel().getSelectedItem();

        if (selectedItem.getParent() == null) return;

        File file = new File(FilePaths.getProgLangCompletationsDirectory() + selectedItem.getValue() + ".json");

        if (file.exists())
        {
            file.delete();
        }

        keyWordsTreeView.getRoot().getChildren().remove(selectedItem);
    }
    
    public void addLanguage()
    {
        String languageName = Dialogs.insertTextDialog("Insert language name", "Insert the name of the language");

        if (languageName == null || languageName.isEmpty() || languageName.isBlank()) return;

        LangCompletations.createNewLangCompletations(languageName);

        IconTreeItem<String> item = new IconTreeItem<>(languageName, languageName + ".png");

        keyWordsTreeView.getRoot().getChildren().add(item);
    }
    
    public void addKeyword()
    {
        if (actualCompletations == null) return;

        String selectedItem = Dialogs.insertTextDialog("Insert keyword", "Insert the keyword to add");
        
        if (selectedItem == null || selectedItem.isEmpty() || selectedItem.isBlank()) return;
        
        actualCompletations.getKeywordsCompletations().add(new KeyWordCompletation(selectedItem));
        
        actualCompletations.saveToFile();
        
        loadKeywords();
    }

    public void removeKeyword()
    {
        if (actualCompletations == null) return;

        String selectedItem = keyWordsListView.getSelectionModel().getSelectedItem();
        
        if (selectedItem == null) return;
        
        actualCompletations.getKeywordsCompletations().removeIf(variable -> variable.getValue().equals(selectedItem));
        
        actualCompletations.saveToFile();
        
        loadKeywords();
    }
}
