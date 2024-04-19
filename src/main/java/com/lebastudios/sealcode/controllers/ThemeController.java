package com.lebastudios.sealcode.controllers;

import com.lebastudios.sealcode.applogic.config.GlobalConfig;
import com.lebastudios.sealcode.applogic.events.AppEvents;
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
        File file = new File("src/main/resources");

        for (File theme : file.listFiles())
        {
            if (!theme.isDirectory()) continue;
            if (theme.getName().equals("com")) continue;

            themeChoiceBox.getItems().add(theme.getName());
        }
        
        themeChoiceBox.setValue(GlobalConfig.getStaticInstance().editorConfig.theme);
    }
}
