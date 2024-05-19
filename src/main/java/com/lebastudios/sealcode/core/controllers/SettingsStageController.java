package com.lebastudios.sealcode.core.controllers;

import com.lebastudios.sealcode.SealCodeApplication;
import com.lebastudios.sealcode.core.controllers.settingsPanels.SettingsPaneController;
import com.lebastudios.sealcode.core.frontend.fxextends.IconTreeItem;
import com.lebastudios.sealcode.core.frontend.fxextends.SettingsTreeView;
import com.lebastudios.sealcode.core.frontend.stages.SettingsStage;
import com.lebastudios.sealcode.events.AppEvents;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;

import java.io.IOException;

public class SettingsStageController implements IStageController
{
    private static SettingsStageController instance;

    @FXML private SplitPane mainSplitPane;
    @FXML private SettingsTreeView mainSettingsTreeView;

    private SettingsPaneController paneController;
    
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
        if (paneController == null) return;

        paneController.apply();
    }
    
    @FXML
    public void aceptSettings()
    {
        applySettings();
        
        SettingsStage.getInstance().close();
    }
    
    public void loadNewSettingsPane(String paneName)
    {
        applySettings();
        
        try
        {
            double dividerPosition = mainSplitPane.getDividerPositions()[0];
            
            FXMLLoader loader = new FXMLLoader(SealCodeApplication.class.getResource(paneName));

            mainSplitPane.getItems().add(loader.load());

            mainSplitPane.getItems().remove(1);

            paneController = loader.getController();
            
            paneController.start();
            
            mainSplitPane.setDividerPositions(dividerPosition);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
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
