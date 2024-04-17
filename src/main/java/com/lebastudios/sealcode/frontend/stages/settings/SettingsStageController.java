package com.lebastudios.sealcode.frontend.stages.settings;

import com.lebastudios.sealcode.SealCodeApplication;
import com.lebastudios.sealcode.frontend.fxextends.SettingsTreeView;
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

    public void loadNewSettingsPane(String PaneName)
    {
        try
        {
            mainSplitPane.getItems().remove(1);
            mainSplitPane.getItems().add(new FXMLLoader(SealCodeApplication.class.getResource(PaneName)).load());
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
