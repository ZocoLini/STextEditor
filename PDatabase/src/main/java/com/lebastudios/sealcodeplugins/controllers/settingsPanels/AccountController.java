package com.lebastudios.sealcodeplugins.controllers.settingsPanels;

import com.lebastudios.sealcode.controllers.MainStageController;
import com.lebastudios.sealcode.controllers.SettingsStageController;
import com.lebastudios.sealcode.controllers.SettingsPaneController;
import com.lebastudios.sealcode.frontend.Notification;
import com.lebastudios.sealcode.MessageType;
import com.lebastudios.sealcodeplugins.DatabasePlugin;
import com.lebastudios.sealcodeplugins.database.MainDBManager;
import com.lebastudios.sealcodeplugins.database.MongoDBManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;

public class AccountController extends SettingsPaneController
{
    public static void loadPane()
    {
        final var fxmlLoader =   new FXMLLoader(DatabasePlugin.class.getResource(
                "settingsScenePanels/accountAnchorPane.fxml"));
        fxmlLoader.setController(new AccountController());

        SettingsStageController.getInstance().loadNewSettingsPane(fxmlLoader);
    }
    
    @FXML
    public TextField usernameField;
    @FXML
    public TextField password;

    @Override
    public void initialize()
    {
        if (MainDBManager.getInstance().isAnyAccountConnected())
        {
            LoggedAccountController.loadPane();
        }
    }

    @Override
    public void apply()
    {
    }

    @FXML
    public void login()
    {
        if (MainDBManager.getInstance().logIn(usernameField.getText(), password.getText()))
        {
            LoggedAccountController.loadPane();

            MongoDBManager.getInstance().pullUserFiles();
        } else
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
