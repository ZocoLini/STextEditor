package com.lebastudios.sealcode.core.controllers.settingsPanels;

import com.lebastudios.sealcode.core.logic.config.GlobalConfig;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FuenteController extends SettingsPaneController
{
    @FXML private TextField fontNameTF;
    @FXML private TextField letterSizeTF;

    @Override
    public void start()
    {
        fontNameTF.setText(GlobalConfig.getStaticInstance().editorConfig.font);
        letterSizeTF.setText(String.valueOf(GlobalConfig.getStaticInstance().editorConfig.fontSize));
    }
    
    @Override
    public void apply()
    {
        GlobalConfig.getStaticInstance().editorConfig.font = fontNameTF.getText();
        GlobalConfig.getStaticInstance().editorConfig.fontSize = Integer.parseInt(letterSizeTF.getText());
    }
}
