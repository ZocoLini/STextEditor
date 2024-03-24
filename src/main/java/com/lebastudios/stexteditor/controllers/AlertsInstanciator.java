package com.lebastudios.stexteditor.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertsInstanciator
{
    public static boolean confirmationDialog(String title, String message)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        return alert.showAndWait().filter(buttonType -> buttonType == ButtonType.OK).isPresent();
    }
}
