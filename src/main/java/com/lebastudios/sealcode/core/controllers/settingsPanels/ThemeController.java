package com.lebastudios.sealcode.core.controllers.settingsPanels;

import com.lebastudios.sealcode.core.logic.config.FilePaths;
import com.lebastudios.sealcode.core.logic.config.GlobalConfig;
import com.lebastudios.sealcode.events.AppEvents;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.io.File;

public class ThemeController extends SettingsPaneController
{
    @FXML public ChoiceBox<String> themeChoiceBox;
    
    @Override
    public void initialize()
    {
        loadAvailableThemes();
    }

    @Override
    public void apply()
    {
        GlobalConfig.getStaticInstance().editorConfig.theme = themeChoiceBox.getValue();

        AppEvents.onThemeChange.invoke();
    }
    
    private void loadAvailableThemes()
    {
        File file = new File(FilePaths.getThemeDirectory());

        themeChoiceBox.getItems().clear();
        
        for (File theme : file.listFiles())
        {
            if (!theme.isDirectory()) continue;
            
            themeChoiceBox.getItems().add(theme.getName());
        }
        
        themeChoiceBox.setValue(GlobalConfig.getStaticInstance().editorConfig.theme);
    }
}
