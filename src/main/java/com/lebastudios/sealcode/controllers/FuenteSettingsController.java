package com.lebastudios.sealcode.controllers;

import com.lebastudios.sealcode.applogic.config.GlobalConfig;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FuenteSettingsController extends Controller
{
    @FXML public TextField fontNameTF;
    @FXML public TextField letterSizeTF;

    @Override
    public void initialize()
    {
        fontNameTF.setText(GlobalConfig.getStaticInstance().editorConfig.font);
        letterSizeTF.setText(String.valueOf(GlobalConfig.getStaticInstance().editorConfig.fontSize));
    }
}
