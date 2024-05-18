package com.lebastudios.sealcode.custom.controllers.settingsPanels;

import com.lebastudios.sealcode.core.controllers.MainStageController;
import com.lebastudios.sealcode.core.controllers.SettingsStageController;
import com.lebastudios.sealcode.core.controllers.settingsPanels.SettingsPaneController;
import com.lebastudios.sealcode.core.frontend.fxextends.Notification;
import com.lebastudios.sealcode.custom.logic.database.MainDBManager;
import com.lebastudios.sealcode.custom.logic.database.MongoDBManager;
import com.lebastudios.sealcode.global.MessageType;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AccountController extends SettingsPaneController
{
    @FXML public TextField usernameField;
    @FXML public TextField password;

    @Override
    public void start()
    {
        if (MainDBManager.getInstance().isAnyAccountConnected()) 
        {
            SettingsStageController.getInstance().loadNewSettingsPane("custom/settingsScenePanels/loggedAccountAnchorPane.fxml");
        }
    }

    @Override
    public void apply() {}

    @FXML
    public void login()
    {
        if (MainDBManager.getInstance().logIn(usernameField.getText(), password.getText()))
        {
            SettingsStageController.getInstance().loadNewSettingsPane("custom/settingsScenePanels/loggedAccountAnchorPane.fxml");

            MongoDBManager.getInstance().pullUserFiles();
        }
        else 
        {
            MainStageController.getInstance().addNotification(new Notification(
                    "Error al intentar iniciar la sesión, por favor, inténtelo de nuevo.",
                    MessageType.Error
            ));    
        }
    }

    @FXML
    public void createAcc()
    {
        if (!MainDBManager.getInstance().register(usernameField.getText(), password.getText())) 
        {
            MainStageController.getInstance().addNotification(new Notification(
                    "Ha ocurrido un error al crear la cuenta, por favor, inténtelo de nuevo más tarde.",
                            MessageType.Error)
            );
            return;
        }
        
        MainStageController.getInstance().addNotification(new Notification(
                "Cuenta creada con éxito, por favor, inicie sesión.",
                MessageType.Info)
        );
        
        usernameField.setText("");
        password.setText("");
    }
}
