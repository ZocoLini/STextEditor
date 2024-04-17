package com.lebastudios.sealcode.controllers;

import com.lebastudios.sealcode.SealCodeApplication;
import com.lebastudios.sealcode.frontend.fxextends.treeviews.SettingsTreeView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SettingsStageController
{
    private static SettingsStageController instance;
    
    @FXML private SettingsTreeView settingsTreeView;
    @FXML private AnchorPane optionsAnchorPane;
    @FXML private SplitPane mainSplitPane;

    public SettingsStageController()
    {
        instance = this;
    }

    public void loadNewSettingsPane(String paneName)
    {
        try
        {
            double dividerPosition = mainSplitPane.getDividerPositions()[0];
            
            FXMLLoader loader = new FXMLLoader(SealCodeApplication.class.getResource("settingsPanels/" + paneName));
            
            mainSplitPane.getItems().remove(1);
            mainSplitPane.getItems().add(loader.load());

            ((Controller) loader.getController()).initialize();
            
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
}
