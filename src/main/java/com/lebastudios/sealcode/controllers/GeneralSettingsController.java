package com.lebastudios.sealcode.controllers;

import com.lebastudios.sealcode.applogic.config.GlobalConfig;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class GeneralSettingsController extends Controller
{
    @FXML public TextField indentationTF;
    @FXML public TextField tabSizeTF;

    @Override
    public void initialize()
    {
        indentationTF.setText(String.valueOf(GlobalConfig.getStaticInstance().editorConfig.indentation));
        tabSizeTF.setText(String.valueOf(GlobalConfig.getStaticInstance().editorConfig.tabSize));
    }
}
