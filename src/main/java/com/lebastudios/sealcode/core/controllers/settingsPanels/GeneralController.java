package com.lebastudios.sealcode.core.controllers.settingsPanels;

import com.lebastudios.sealcode.core.logic.config.GlobalConfig;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class GeneralController extends SettingsPaneController
{
    @FXML public TextField indentationTF;
    @FXML public TextField tabSizeTF;

    @Override
    public void initialize()
    {
        indentationTF.setText(String.valueOf(GlobalConfig.getStaticInstance().editorConfig.indentation));
        tabSizeTF.setText(String.valueOf(GlobalConfig.getStaticInstance().editorConfig.tabSize));
    }

    @Override
    public void apply()
    {
        GlobalConfig.getStaticInstance().editorConfig.indentation = Integer.parseInt(indentationTF.getText());
        GlobalConfig.getStaticInstance().editorConfig.tabSize = Integer.parseInt(tabSizeTF.getText());
    }
}
