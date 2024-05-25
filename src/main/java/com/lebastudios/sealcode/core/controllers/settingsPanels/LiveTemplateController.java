package com.lebastudios.sealcode.core.controllers.settingsPanels;

import com.lebastudios.sealcode.core.frontend.dialogs.Dialogs;
import com.lebastudios.sealcode.core.frontend.fxextends.IconTreeItem;
import com.lebastudios.sealcode.core.logic.config.FilePaths;
import com.lebastudios.sealcode.core.logic.fileobj.JsonFile;
import com.lebastudios.sealcode.core.logic.fileobj.LangCompletationsJSON;
import com.lebastudios.sealcode.global.FileOperation;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;

public class LiveTemplateController
{
    @FXML private TreeView<String> liveTemplateTreeView;
    @FXML private ListView<String> liveTemplatesListView;
    
    @FXML private TextArea liveTemplateCompTextArea;
    @FXML private TextArea liveTemplateDescTextArea;
    @FXML private TextField liveTemplateValueTextField;

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

            liveTemplateTreeView.getRoot().getChildren().add(item);
        }

        liveTemplateTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue == null) return;
            
            clearTextAreas();
            loadLiveTemplates();
        });

        liveTemplatesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue == null) return;
            
            clearTextAreas();
            loadLiveTemplate();
        });
        
        liveTemplateTreeView.getRoot().setExpanded(true);
    }

    private void loadLiveTemplate()
    {
        String selectedItem = liveTemplatesListView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) return;

        LangCompletationsJSON.LiveTemplateCompletation liveTemplateCompletation = actualCompletations.get().liveTemplatesCompletations.stream()
                .filter(variable -> variable.getValue().equals(selectedItem))
                .findFirst()
                .orElse(null);
        
        if (liveTemplateCompletation == null) return;

        liveTemplateValueTextField.setText(liveTemplateCompletation.getValue());
        liveTemplateDescTextArea.setText(liveTemplateCompletation.getDescription());
        liveTemplateCompTextArea.setText(liveTemplateCompletation.getCompletation());
    }
    
    @FXML
    private void saveLiveTemplate()
    {
        if (actualCompletations == null) return;
        if (liveTemplateValueTextField.getText().isEmpty() || liveTemplateValueTextField.getText().isBlank()) return;
        
        actualCompletations.get().liveTemplatesCompletations.removeIf(
                variable -> variable.getValue().equals(liveTemplateValueTextField.getText())
        );
        actualCompletations.get().liveTemplatesCompletations.add(new LangCompletationsJSON.LiveTemplateCompletation(
                liveTemplateValueTextField.getText(),
                liveTemplateDescTextArea.getText(),
                liveTemplateCompTextArea.getText()
        ));
        
        actualCompletations.write();

        clearTextAreas();

        loadLiveTemplates();
    }

    private void clearTextAreas()
    {
        liveTemplateValueTextField.setText("");
        liveTemplateDescTextArea.setText("");
        liveTemplateCompTextArea.setText("");
    }

    private void loadLiveTemplates()
    {
        TreeItem<String> selectedItem = liveTemplateTreeView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) return;
        if (selectedItem.getParent() == null) return;

        actualCompletations = new JsonFile<>(
                new File(FilePaths.getCompletationsDir() + selectedItem.getValue() + ".json"),
                new LangCompletationsJSON());
        
        liveTemplatesListView.getItems().clear();

        for (var liveTemplateCompletation : actualCompletations.get().liveTemplatesCompletations)
        {
            liveTemplatesListView.getItems().add(liveTemplateCompletation.getValue());
        }
    }

    @FXML
    private void addLiveTemplate()
    {
        if (actualCompletations == null) return;

        liveTemplateValueTextField.setText("<new live template>");
        liveTemplateDescTextArea.setText("<description>");
        liveTemplateCompTextArea.setText("<completation>");

        actualCompletations.write();

        loadLiveTemplates();
    }

    @FXML
    private void removeLiveTemplate()
    {
        if (actualCompletations == null) return;

        String selectedItem = liveTemplatesListView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) return;

        actualCompletations.get().liveTemplatesCompletations.removeIf(variable -> variable.getValue().equals(selectedItem));

        clearTextAreas();

        actualCompletations.write();

        loadLiveTemplates();
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

        liveTemplateTreeView.getRoot().getChildren().add(item);
    }

    @FXML
    private void removeLanguage()
    {
        TreeItem<String> selectedItem = liveTemplateTreeView.getSelectionModel().getSelectedItem();

        if (selectedItem.getParent() == null) return;

        File file = new File(FilePaths.getCompletationsDir() + selectedItem.getValue() + ".json");

        if (file.exists())
        {
            file.delete();
        }

        liveTemplateTreeView.getRoot().getChildren().remove(selectedItem);
    }
}
