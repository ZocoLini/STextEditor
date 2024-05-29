package com.lebastudios.sealcode.controllers.settingsPanels;

import com.lebastudios.sealcode.frontend.Dialogs;
import com.lebastudios.sealcode.frontend.IconTreeItem;
import com.lebastudios.sealcode.FilePaths;
import com.lebastudios.sealcode.fileobj.JsonFile;
import com.lebastudios.sealcode.fileobj.LangCompletationsJSON;
import com.lebastudios.sealcode.FileOperation;
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
        loadTreeView();

        liveTemplateTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue == null) return;
            if (newValue.getParent() == null) return;

            actualCompletations = new JsonFile<>(
                    new File(FilePaths.getCompletationsDir() + newValue.getValue() + ".json"),
                    new LangCompletationsJSON());
            
            clearTextAreas();
            loadListView();
        });

        liveTemplatesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue == null) return;
            
            clearTextAreas();
            loadLiveTemplate();
        });
        
        liveTemplateTreeView.getRoot().setExpanded(true);
    }

    private void loadTreeView()
    {
        File completationsFolder = new File(FilePaths.getCompletationsDir());

        liveTemplateTreeView.getRoot().getChildren().clear();
        
        for (var file : completationsFolder.listFiles())
        {
            IconTreeItem<String> item = new IconTreeItem<>(
                    FileOperation.getFileName(file),
                    "ext_" + FileOperation.getFileName(file) + ".png"
            );

            liveTemplateTreeView.getRoot().getChildren().add(item);
        }
    }

    private void loadListView()
    {
        liveTemplatesListView.getItems().clear();

        for (var liveTemplateCompletation : actualCompletations.get().liveTemplatesCompletations)
        {
            liveTemplatesListView.getItems().add(liveTemplateCompletation.getValue());
        }
    }
    
    private void loadLiveTemplate()
    {
        String selectedItem = liveTemplatesListView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) return;

        var liveTemplateCompletation = actualCompletations.get().getLiveTemplateCompletation(selectedItem);
        
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

        loadListView();
    }

    private void clearTextAreas()
    {
        liveTemplateValueTextField.setText("");
        liveTemplateDescTextArea.setText("");
        liveTemplateCompTextArea.setText("");
    }

    @FXML
    private void addLiveTemplate()
    {
        if (actualCompletations == null) return;

        liveTemplateValueTextField.setText("<new live template>");
        liveTemplateDescTextArea.setText("<description>");
        liveTemplateCompTextArea.setText("<completation>");

        actualCompletations.write();

        loadListView();
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

        loadListView();
    }

    @FXML
    private void addLanguage()
    {
        String languageName = Dialogs.insertTextDialog("Insert language styleClass", "Insert the styleClass of the language");

        if (languageName == null || languageName.isEmpty() || languageName.isBlank()) return;

        JsonFile.createNewFile(new File(FilePaths.getCompletationsDir()), languageName);

        loadTreeView();
    }

    @FXML
    private void removeLanguage()
    {
        actualCompletations.delete();
        loadTreeView();
    }
}
