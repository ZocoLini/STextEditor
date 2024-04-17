package com.lebastudios.sealcode.frontend.stages.settings;

import com.lebastudios.sealcode.frontend.fxextends.SettingsTreeView;
import javafx.fxml.FXML;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

public class SettingsStageController
{
    private static SettingsStageController instance;
    
    @FXML public SettingsTreeView settingsTreeView;

    public SettingsStageController()
    {
        instance = this;
    }

    public static SettingsStageController getInstance()
    {
        return instance;
    }
}
