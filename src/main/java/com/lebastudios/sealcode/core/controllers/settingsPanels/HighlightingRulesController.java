package com.lebastudios.sealcode.core.controllers.settingsPanels;

import com.lebastudios.sealcode.core.frontend.dialogs.Dialogs;
import com.lebastudios.sealcode.core.frontend.fxextends.IconTreeItem;
import com.lebastudios.sealcode.core.logic.config.FilePaths;
import com.lebastudios.sealcode.core.logic.fileobj.HighlightingRulesJSON;
import com.lebastudios.sealcode.core.logic.fileobj.JsonFile;
import com.lebastudios.sealcode.core.logic.fileobj.LangCompletationsJSON;
import com.lebastudios.sealcode.global.FileOperation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;

public class HighlightingRulesController
{
    @FXML public ListView<String> rulesListView;
    @FXML private TreeView<String> highlightingRulesFilesTreeView;

    @FXML private TextField name;
    @FXML private TextField styleClass;
    @FXML private TextField findableRegex;
    @FXML private TextField coloureableRegex;

    private JsonFile<HighlightingRulesJSON> actualHighlightingRules;
    private HighlightingRulesJSON.HighlightingRule actualRule;
    
    public void initialize()
    {
        for (var file : new File(FilePaths.getHighlightingRulesDir()).listFiles())
        {
            if (file.getName().equals("equivalentExtensions.json")) continue;
            
            highlightingRulesFilesTreeView.getRoot().getChildren().add(new IconTreeItem<>(
                    file.getName().replace(".json", ""),
                    "ext_" + FileOperation.getFileName(file) + ".png"
            ));
        }
        
        highlightingRulesFilesTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue == null) return;

            actualHighlightingRules = new JsonFile<>(
                    new File(FilePaths.getHighlightingRulesDir() + newValue.getValue() + ".json"),
                    new HighlightingRulesJSON());
            
            loadListView();
        });
        
        rulesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue == null) return;
            
            actualRule = actualHighlightingRules.getInstance().getHighlightingRule(newValue);
            
            loadRule();
        });
        
        highlightingRulesFilesTreeView.getRoot().setExpanded(true);
    }
    
    private void loadListView()
    {
        rulesListView.getItems().clear();
        for (var rule : actualHighlightingRules.getInstance().rules)
        {
            if (rule.name.equals(HighlightingRulesJSON.KEYWORDS_RULE_NAME)) continue;
            
            rulesListView.getItems().add(rule.name);
        }
    }
    
    private void loadRule()
    {
        var rule = actualHighlightingRules.getInstance().getHighlightingRule(actualRule.name);
        name.setText(rule.name);
        styleClass.setText(rule.styleClass);
        findableRegex.setText(rule.regex);
        coloureableRegex.setText(rule.highlightRegex);
    }

    public void createNewRulesFile()
    {
        String fileName = Dialogs.insertTextDialog("Enter de name of the extension", "Extension name");
        if (fileName == null || fileName.isEmpty() || fileName.isBlank()) return;

        JsonFile.createNewFile(FilePaths.getHighlightingRulesDir(), fileName);

        IconTreeItem<String> item = new IconTreeItem<>(fileName, fileName + ".png");

        highlightingRulesFilesTreeView.getRoot().getChildren().add(item);
    }

    public void deleteRulesFile()
    {
        TreeItem<String> selectedItem = highlightingRulesFilesTreeView.getSelectionModel().getSelectedItem();

        if (selectedItem.getParent() == null) return;

        File file = new File(FilePaths.getHighlightingRulesDir() + selectedItem.getValue() + ".json");

        if (file.exists())
        {
            file.delete();
        }

        highlightingRulesFilesTreeView.getRoot().getChildren().remove(selectedItem);
    }

    public void createRule()
    {
        name.setText("<name>");
        styleClass.setText("<styleClass>");
        findableRegex.setText("<findableRegex>");
        coloureableRegex.setText("<coloureableRegex>");
    }

    private void deleteRule(String name)
    {
        actualHighlightingRules.getInstance().removeHighlightingRule(name);
        actualHighlightingRules.writeToFile();
        loadListView();
    }
    
    public void deleteRule()
    {
        deleteRule(rulesListView.getSelectionModel().getSelectedItem());
    }

    public void saveRule()
    {
        if (actualHighlightingRules == null) return;
        
        if (actualRule == null) 
        {
            HighlightingRulesJSON.HighlightingRule rule = new HighlightingRulesJSON.HighlightingRule(
                    name.getText(),
                    styleClass.getText(),
                    findableRegex.getText(),
                    coloureableRegex.getText()
            );
            actualHighlightingRules.getInstance().rules.add(rule);
        }
        else
        {
            actualRule.name = name.getText();
            actualRule.styleClass = styleClass.getText();
            actualRule.regex = findableRegex.getText();
            actualRule.highlightRegex = coloureableRegex.getText();
        }
        
        actualHighlightingRules.writeToFile();
        loadListView();
    }
}
