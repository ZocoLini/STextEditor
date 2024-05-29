package com.lebastudios.sealcode.controllers.settingsPanels;

import com.lebastudios.sealcode.frontend.Dialogs;
import com.lebastudios.sealcode.frontend.IconTreeItem;
import com.lebastudios.sealcode.FilePaths;
import com.lebastudios.sealcode.fileobj.HighlightingRulesJSON;
import com.lebastudios.sealcode.fileobj.JsonFile;
import com.lebastudios.sealcode.FileOperation;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
        loadTreeView();

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
            
            actualRule = actualHighlightingRules.get().getHighlightingRule(newValue);
            
            loadRule();
        });
        
        highlightingRulesFilesTreeView.getRoot().setExpanded(true);
    }

    private void loadTreeView()
    {
        highlightingRulesFilesTreeView.getRoot().getChildren().clear();
        
        for (var file : new File(FilePaths.getHighlightingRulesDir()).listFiles())
        {
            if (file.getName().equals("equivalentExtensions.json")) continue;

            highlightingRulesFilesTreeView.getRoot().getChildren().add(new IconTreeItem<>(
                    file.getName().replace(".json", ""),
                    "ext_" + FileOperation.getFileName(file) + ".png"
            ));
        }
    }

    private void loadListView()
    {
        rulesListView.getItems().clear();
        for (var rule : actualHighlightingRules.get().rules)
        {
            if (rule.name.equals(HighlightingRulesJSON.KEYWORDS_RULE_NAME)) continue;
            
            rulesListView.getItems().add(rule.name);
        }
    }
    
    private void clear()
    {
        name.clear();
        styleClass.clear();
        findableRegex.clear();
        coloureableRegex.clear();
    }
    
    private void loadRule()
    {
        var rule = actualHighlightingRules.get().getHighlightingRule(actualRule.name);
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

        loadTreeView();
    }

    public void deleteRulesFile()
    {
        actualHighlightingRules.delete();
        loadTreeView();
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
        actualHighlightingRules.get().removeHighlightingRule(name);
        actualHighlightingRules.write();
        loadListView();
    }
    
    public void deleteRule()
    {
        deleteRule(rulesListView.getSelectionModel().getSelectedItem());
    }

    public void saveRule()
    {
        String invalidPattern = "<[^<>]*>]";
        if (name.getText().matches(invalidPattern) || styleClass.getText().matches(invalidPattern) 
                || findableRegex.getText().matches(invalidPattern) 
                || coloureableRegex.getText().matches(invalidPattern)) 
        {
            clear();
            return;
        }
        
        if (actualHighlightingRules == null) return;
        if (name.getText().isEmpty() || styleClass.getText().isEmpty() || findableRegex.getText().isEmpty())
        {
            clear();
            return;
        }
        if (name.getText().isBlank() || styleClass.getText().isBlank() || findableRegex.getText().isBlank()) 
        {
            clear();
            return;
        }
        
        if (findableRegex.getText().isBlank()) findableRegex.setText("");
        
        if (actualRule == null) 
        {
            HighlightingRulesJSON.HighlightingRule rule = new HighlightingRulesJSON.HighlightingRule(
                    name.getText(),
                    styleClass.getText(),
                    findableRegex.getText(),
                    coloureableRegex.getText()
            );
            actualHighlightingRules.get().rules.add(rule);
        }
        else
        {
            actualRule.name = name.getText();
            actualRule.styleClass = styleClass.getText();
            actualRule.regex = findableRegex.getText();
            actualRule.highlightRegex = coloureableRegex.getText();
        }
        
        actualHighlightingRules.write();
        loadListView();
    }
}
