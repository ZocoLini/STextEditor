package com.lebastudios.sealcode.custom.controllers.settingsPanels;

import com.lebastudios.sealcode.core.controllers.MainStageController;
import com.lebastudios.sealcode.core.controllers.SettingsStageController;
import com.lebastudios.sealcode.core.controllers.settingsPanels.SettingsPaneController;
import com.lebastudios.sealcode.core.frontend.fxextends.Notification;
import com.lebastudios.sealcode.custom.logic.database.MainDBManager;
import com.lebastudios.sealcode.custom.logic.database.MongoDBManager;
import com.lebastudios.sealcode.custom.logic.database.User;
import com.lebastudios.sealcode.global.MessageType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoggedAccountController extends SettingsPaneController
{
    @FXML public Label username;

    @Override
    public void start()
    {
        username.setText(User.Deserialize().userName());
    }

    @Override
    public void apply()
    {

    }

    @FXML
    public void logOut()
    {
        MongoDBManager.getInstance().pushUserFiles();
        MainDBManager.getInstance().logOut();
        
        SettingsStageController.getInstance().loadNewSettingsPane("custom/settingsScenePanels/accountAnchorPane.fxml");
    }

    public void pushSettings()
    {
        if (!MongoDBManager.getInstance().pushUserFiles()) 
        {
            MainStageController.getInstance().addNotification(new Notification(
                    "An error occurred while pushing the settings to the server.",
                    MessageType.Error
            ));
        };
    }

    public void pullSettings()
    {
        if (!MongoDBManager.getInstance().pullUserFiles()) 
        {
            MainStageController.getInstance().addNotification(new Notification(
                    "An error occurred while pulling the settings from the server.",
                    MessageType.Error
            ));
        };
    }
}
