package com.lebastudios.sealcode.controllers;

import com.lebastudios.sealcode.applogic.config.GlobalConfig;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class PreferenciasController extends SettingsPaneController
{
    @FXML public CheckBox ignoreGitDirCB;

    @Override
    public void initialize()
    {
        ignoreGitDirCB.setSelected(GlobalConfig.getStaticInstance().userPrefs.ignoreGitDir);
    }

    @Override
    public void apply()
    {
        GlobalConfig.getStaticInstance().userPrefs.ignoreGitDir = ignoreGitDirCB.isSelected();
    }
}
