package com.lebastudios.sealcode.core.controllers.settingsPanels;

import com.lebastudios.sealcode.core.frontend.dialogs.Dialogs;
import com.lebastudios.sealcode.core.frontend.fxextends.IconTreeItem;
import com.lebastudios.sealcode.core.logic.config.FilePaths;
import com.lebastudios.sealcode.core.logic.defaultcompletations.LangCompletations;
import com.lebastudios.sealcode.core.logic.defaultcompletations.LiveTemplateCompletation;
import com.lebastudios.sealcode.global.FileOperation;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;

public class LiveTemplateController
{
    @FXML public TreeView<String> liveTemplateTreeView;
    @FXML public ListView<String> liveTemplatesListView;
    
    @FXML public TextArea liveTemplateCompTextArea;
    @FXML public TextArea liveTemplateDescTextArea;
    @FXML public TextField liveTemplateValueTextField;

    private LangCompletations actualCompletations;
    
    // TODO: Ajustar el guardado. Funciona mal.
    
    public void initialize()
    {
        File completationsFolder = new File(FilePaths.getProgLangCompletationsDirectory());

        for (var file : completationsFolder.listFiles())
        {
            IconTreeItem<String> item = new IconTreeItem<>(
                    FileOperation.getFileName(file),
                    FileOperation.getFileExtension(file) + ".png"
            );

            liveTemplateTreeView.getRoot().getChildren().add(item);

            liveTemplateTreeView.setOnMouseClicked(event ->
            {
                loadLiveTemplates();
            });
            
            liveTemplatesListView.setOnMouseClicked(event ->
            {
                loadLiveTemplate();
            });
        }

        liveTemplateTreeView.getRoot().setExpanded(true);
    }

    private void loadLiveTemplate()
    {
        clearAndSaveLiveTemplateEditing();
        String selectedItem = liveTemplatesListView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) return;

        LiveTemplateCompletation liveTemplateCompletation = actualCompletations.getLiveTemplatesCompletations().stream()
                .filter(variable -> variable.getValue().equals(selectedItem))
                .findFirst()
                .orElse(null);
        
        if (liveTemplateCompletation == null) return;

        liveTemplateValueTextField.setText(liveTemplateCompletation.getValue());
        liveTemplateDescTextArea.setText(liveTemplateCompletation.getDescription());
        liveTemplateCompTextArea.setText(liveTemplateCompletation.getCompletation());
    }
    
    private void clearAndSaveLiveTemplateEditing()
    {
        if (actualCompletations == null) return;
        if (liveTemplateValueTextField.getText().isEmpty() || liveTemplateValueTextField.getText().isBlank()) return;
        
        actualCompletations.getLiveTemplatesCompletations().removeIf(variable -> variable.getValue().equals(liveTemplateValueTextField.getText()));
        actualCompletations.getLiveTemplatesCompletations().add(new LiveTemplateCompletation(
                liveTemplateValueTextField.getText(),
                liveTemplateDescTextArea.getText(),
                liveTemplateCompTextArea.getText()
        ));
        
        actualCompletations.saveToFile();
        loadLiveTemplates();
        
        liveTemplateValueTextField.setText("");
        liveTemplateDescTextArea.setText("");
        liveTemplateCompTextArea.setText("");
    }
    
    private void loadLiveTemplates()
    {
        TreeItem<String> selectedItem = liveTemplateTreeView.getSelectionModel().getSelectedItem();

        if (selectedItem.getParent() == null) return;

        actualCompletations = LangCompletations.readCompletationFromFile(selectedItem.getValue());

        liveTemplatesListView.getItems().clear();

        for (var liveTemplateCompletation : actualCompletations.getLiveTemplatesCompletations())
        {
            liveTemplatesListView.getItems().add(liveTemplateCompletation.getValue());
        }
    }

    public void addLiveTemplate()
    {
        if (actualCompletations == null) return;

        String selectedItem = Dialogs.insertTextDialog("Insert keyword", "Insert the keyword to add");

        if (selectedItem == null || selectedItem.isEmpty() || selectedItem.isBlank()) return;

        actualCompletations.getLiveTemplatesCompletations().add(new LiveTemplateCompletation(
                selectedItem, 
                "", 
                "completation en proceso"
        ));

        actualCompletations.saveToFile();

        loadLiveTemplates();
    }

    public void removeLiveTemplate()
    {
        if (actualCompletations == null) return;

        String selectedItem = liveTemplatesListView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) return;

        actualCompletations.getLiveTemplatesCompletations().removeIf(variable -> variable.getValue().equals(selectedItem));

        actualCompletations.saveToFile();

        loadLiveTemplates();
    }

    public void addLanguage()
    {
        String languageName = Dialogs.insertTextDialog("Insert language name", "Insert the name of the language");

        if (languageName == null || languageName.isEmpty() || languageName.isBlank()) return;

        LangCompletations.createNewLangCompletations(languageName);

        IconTreeItem<String> item = new IconTreeItem<>(languageName, languageName + ".png");

        liveTemplateTreeView.getRoot().getChildren().add(item);
    }

    public void removeLanguage()
    {
        TreeItem<String> selectedItem = liveTemplateTreeView.getSelectionModel().getSelectedItem();

        if (selectedItem.getParent() == null) return;

        File file = new File(FilePaths.getProgLangCompletationsDirectory() + selectedItem.getValue() + ".json");

        if (file.exists())
        {
            file.delete();
        }

        liveTemplateTreeView.getRoot().getChildren().remove(selectedItem);
    }
}
