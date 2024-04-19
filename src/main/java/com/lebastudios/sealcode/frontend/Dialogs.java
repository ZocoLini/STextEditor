package com.lebastudios.sealcode.frontend;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Dialogs
{
    public static boolean confirmationDialog(String title, String message)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        return alert.showAndWait().filter(buttonType -> buttonType == ButtonType.OK).isPresent();
    }

    public static String insertTextDialog(String title)
    {
        // Crear un nuevo Stage para el cuadro de diálogo
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        // Crear un campo de texto para ingresar el string
        TextField textField = new TextField();
        textField.setPrefHeight(30);
        textField.setPrefWidth(200);

        dialogStage.addEventHandler(KeyEvent.KEY_PRESSED, event ->
        {
            switch (event.getCode())
            {
                case ENTER:
                    dialogStage.close();
                    break;
                case ESCAPE:
                    textField.setText("");
                    dialogStage.close();
                    break;
            }
        });

        dialogStage.setOnCloseRequest(event ->
        {
            textField.setText("");
            dialogStage.close();
        });

        // Crear la escena del cuadro de diálogo y mostrarla
        Scene dialogScene = new Scene(textField, 300, 30);
        dialogStage.setScene(dialogScene);
        dialogStage.setTitle(title);
        dialogStage.showAndWait();

        return textField.getText();
    }
}
