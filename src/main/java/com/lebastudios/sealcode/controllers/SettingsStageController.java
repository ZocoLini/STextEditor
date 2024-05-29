package com.lebastudios.sealcode.controllers;

import com.lebastudios.sealcode.frontend.IconTreeItem;
import com.lebastudios.sealcode.frontend.SettingsTreeView;
import com.lebastudios.sealcode.frontend.stages.SettingsStage;
import com.lebastudios.sealcode.events.AppEvents;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;

import java.io.IOException;

public class SettingsStageController implements IStageController
{
    private static SettingsStageController instance;

    @FXML private SplitPane mainSplitPane;
    @FXML private SettingsTreeView mainSettingsTreeView;
    
    private SettingsPaneController controller;
    
    public SettingsStageController()
    {
        instance = this;
    }
    
    @FXML
    public void closeSettingsStage()
    {
        SettingsStage.getInstance().close();
    }
    
    @FXML
    public void applySettings()
    {
        if (controller == null) return;

        controller.apply();
    }
    
    @FXML
    public void aceptSettings()
    {
        applySettings();
        
        SettingsStage.getInstance().close();
    }

    public void loadNewSettingsPane(FXMLLoader loader)
    {
        applySettings();

        try
        {
            mainSplitPane.getItems().add(loader.load());
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        this.controller = loader.getController();

        double dividerPosition = mainSplitPane.getDividerPositions()[0];

        mainSplitPane.getItems().remove(1);

        mainSplitPane.setDividerPositions(dividerPosition);
    }
    
    public void loadNewSettingsPane(Node loadedScene, SettingsPaneController controller)
    {
        applySettings();
        
        this.controller = controller;

        double dividerPosition = mainSplitPane.getDividerPositions()[0];

        mainSplitPane.getItems().add(loadedScene);

        mainSplitPane.getItems().remove(1);

        mainSplitPane.setDividerPositions(dividerPosition);
    }
    
    public static SettingsStageController getInstance()
    {
        return instance;
    }

    @Override
    public void customiceStage()
    {
        for (var variable : mainSettingsTreeView.getRoot().getChildren())
        {
            AppEvents.onLoadedSettingsIconTreeItem.invoke((IconTreeItem<String>) variable);
        }
        
        AppEvents.onLoadedSettingsTreeView.invoke(mainSettingsTreeView);
    }
}
