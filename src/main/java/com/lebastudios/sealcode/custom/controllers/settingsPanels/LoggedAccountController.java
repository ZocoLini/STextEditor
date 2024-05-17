package com.lebastudios.sealcode.custom.controllers.settingsPanels;

import com.lebastudios.sealcode.core.controllers.SettingsStageController;
import com.lebastudios.sealcode.core.controllers.settingsPanels.SettingsPaneController;
import com.lebastudios.sealcode.custom.logic.database.MainDBConnection;
import com.lebastudios.sealcode.custom.logic.database.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoggedAccountController extends SettingsPaneController
{
    @FXML public Label username;

    @Override
    public void initialize()
    {
        User actualUser = User.Deserialize();
        
        if (actualUser == null)
        {
            SettingsStageController.getInstance().loadNewSettingsPane("custom/settingsScenePanels/accountAnchorPane.fxml");
            return;
        }
        
        username.setText(User.Deserialize().userName());
    }

    @Override
    public void apply()
    {

    }

    @FXML
    public void logOut()
    {
        MainDBConnection.getInstance().logOut();
        SettingsStageController.getInstance().loadNewSettingsPane("custom/settingsScenePanels/accountAnchorPane.fxml");
    }
}
