package com.lebastudios.sealcode.controllers;

import com.lebastudios.sealcode.applogic.config.GlobalConfig;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class PreferenciasSettingsController extends Controller
{
    @FXML public CheckBox ignoreGitDirCB;

    @Override
    public void initialize()
    {
        ignoreGitDirCB.setSelected(GlobalConfig.getStaticInstance().userPrefs.ignoreGitDir);
    }
}
