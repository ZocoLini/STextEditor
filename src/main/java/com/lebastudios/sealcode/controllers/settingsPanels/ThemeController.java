package com.lebastudios.sealcode.controllers.settingsPanels;

import com.lebastudios.sealcode.FilePaths;
import com.lebastudios.sealcode.controllers.SettingsPaneController;
import com.lebastudios.sealcode.config.GlobalConfig;
import com.lebastudios.sealcode.events.AppEvents;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.io.File;

public class ThemeController extends SettingsPaneController
{
    @FXML private ChoiceBox<String> themeChoiceBox;
    
    @Override
    public void initialize()
    {
        loadAvailableThemes();
    }

    @Override
    public void apply()
    {
        GlobalConfig.getStaticInstance().editorConfig.theme = themeChoiceBox.getValue();

        AppEvents.onGlobalConfigUpdate.invoke();
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
