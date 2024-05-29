package com.lebastudios.sealcodeplugins.controllers.settingsPanels;

import com.lebastudios.sealcode.controllers.MainStageController;
import com.lebastudios.sealcode.controllers.SettingsStageController;
import com.lebastudios.sealcode.controllers.SettingsPaneController;
import com.lebastudios.sealcode.frontend.Notification;
import com.lebastudios.sealcodeplugins.DatabasePlugin;
import com.lebastudios.sealcodeplugins.database.MainDBManager;
import com.lebastudios.sealcodeplugins.database.MongoDBManager;
import com.lebastudios.sealcodeplugins.database.User;
import com.lebastudios.sealcode.MessageType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

public class LoggedAccountController extends SettingsPaneController
{
    public static void loadPane()
    {
        final var fxmlLoader =   new FXMLLoader(DatabasePlugin.class.getResource(
                "settingsScenePanels/loggedAccountAnchorPane.fxml"));
        fxmlLoader.setController(new LoggedAccountController());

        SettingsStageController.getInstance().loadNewSettingsPane(fxmlLoader);
    }
    
    @FXML public Label username;

    @Override
    public void initialize()
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
        
        AccountController.loadPane();
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
