package com.lebastudios.sealcode.frontend.stages.settings;

import javafx.fxml.FXML;
import javafx.scene.control.TreeView;

public class SettingsStageController
{
    private static SettingsStageController instance;
    @FXML public TreeView<String> treeViewMenu;

    public SettingsStageController()
    {
        instance = this;
    }

    public static SettingsStageController getInstance()
    {
        return instance;
    }
}
