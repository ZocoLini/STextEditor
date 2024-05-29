package com.lebastudios.sealcode.controllers.settingsPanels;

import com.lebastudios.sealcode.controllers.SettingsPaneController;
import com.lebastudios.sealcode.config.GlobalConfig;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FuenteController extends SettingsPaneController
{
    @FXML private TextField fontNameTF;
    @FXML private TextField letterSizeTF;

    @Override
    public void initialize()
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
