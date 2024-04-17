package com.lebastudios.sealcode.controllers;

import com.lebastudios.sealcode.applogic.config.GlobalConfig;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.io.File;

public class ThemeSettingsController extends Controller
{
    @FXML public ChoiceBox<String> themeChoiceBox;
    
    @Override
    public void initialize()
    {
        loadAvailableThemes();
    }
    
    private void loadAvailableThemes()
    {
        File file = new File("src/main/resources");

        for (File theme : file.listFiles())
        {
            if (!file.isDirectory()) continue;
            if (file.getName().equals("com")) continue;

            themeChoiceBox.getItems().add(theme.getName());
        }
        
        themeChoiceBox.setValue(GlobalConfig.getStaticInstance().editorConfig.theme);
    }
}
