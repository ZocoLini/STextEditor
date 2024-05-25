package com.lebastudios.sealcode.core.controllers.settingsPanels;

import com.lebastudios.sealcode.core.frontend.dialogs.Dialogs;
import com.lebastudios.sealcode.core.frontend.fxextends.IconTreeItem;
import com.lebastudios.sealcode.core.logic.config.FilePaths;
import com.lebastudios.sealcode.core.logic.fileobj.HighlightingRulesJSON;
import com.lebastudios.sealcode.core.logic.fileobj.JsonFile;
import com.lebastudios.sealcode.core.logic.fileobj.LangCompletationsJSON;
import com.lebastudios.sealcode.global.FileOperation;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighlightingKeywordsController
{
    @FXML private TreeView<String> langsTreeView;
    @FXML private ListView<String> keywordsListView;

    private JsonFile<HighlightingRulesJSON> actualHighlightingRules;
    
    public void initialize()
    {
        loadTreeView();
        
        langsTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue == null) return;

            actualHighlightingRules = new JsonFile<>(
                    new File(FilePaths.getHighlightingRulesDir() + newValue.getValue() + ".json"),
                    new HighlightingRulesJSON());

            loadListView();
        });
    }
    
    private void loadTreeView()
    {
        langsTreeView.getRoot().getChildren().clear();

        for (var file : new File(FilePaths.getHighlightingRulesDir()).listFiles())
        {
            if (file.getName().equals("equivalentExtensions.json")) continue;

            langsTreeView.getRoot().getChildren().add(new IconTreeItem<>(
                    file.getName().replace(".json", ""),
                    "ext_" + FileOperation.getFileName(file) + ".png"
            ));
        }
        
        langsTreeView.getRoot().setExpanded(true);
    }
    
    private void loadListView()
    {
        keywordsListView.getItems().clear();
        
        List<String> keywords = actualHighlightingRules.get().getKeywords();
        
        for (var keyword : keywords)
        {
            keywordsListView.getItems().add(keyword);
        }
    }
    
    public void createLangFile()
    {
        String fileName = Dialogs.insertTextDialog("Create new Lang File");
        if (fileName == null || fileName.isEmpty() || fileName.isBlank()) return;

        JsonFile.createNewFile(FilePaths.getHighlightingRulesDir(), fileName);

        loadTreeView();
    }

    public void deleteLangFile()
    {
        actualHighlightingRules.delete();
        loadTreeView();
    }

    public void addKeyword()
    {
        String newKeyword = Dialogs.insertTextDialog("New Keyword");
        
        List<String> keywords = new ArrayList<>(actualHighlightingRules.get().getKeywords());
        keywords.add(newKeyword);
        
        actualHighlightingRules.get().setKeywords(keywords.toArray(new String[0]));
        
        actualHighlightingRules.write();
        loadListView();
    }

    public void deleteKeyword()
    {
        String keywordToRemove = keywordsListView.getSelectionModel().getSelectedItem();

        List<String> keywords = new ArrayList<>(actualHighlightingRules.get().getKeywords());
        keywords.removeAll(Collections.singleton(keywordToRemove));

        actualHighlightingRules.get().setKeywords(keywords.toArray(new String[0]));

        actualHighlightingRules.write();
        loadListView();
    }

    public void syncCompletationsKeywords()
    {
        String actulaLang = langsTreeView.getSelectionModel().getSelectedItem().getValue();
        
        JsonFile<LangCompletationsJSON> completations = new JsonFile<>(
                new File(FilePaths.getCompletationsDir() + actulaLang + ".json"),
                new LangCompletationsJSON());
        
        List<String> keywords = new ArrayList<>();
        
        for (var keyword : completations.get().keywordsCompletations)
        {
            keywords.add(keyword.getValue());
        }
        
        actualHighlightingRules.get().setKeywords(keywords.toArray(new String[0]));
        
        actualHighlightingRules.write();
        loadListView();
    }
}
