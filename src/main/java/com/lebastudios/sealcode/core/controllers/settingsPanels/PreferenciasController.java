package com.lebastudios.sealcode.core.controllers.settingsPanels;

import com.lebastudios.sealcode.core.logic.config.GlobalConfig;
import com.lebastudios.sealcode.events.AppEvents;
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

        AppEvents.onPreferencesUpdate.invoke();
    }
}
