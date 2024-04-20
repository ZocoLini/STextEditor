package com.lebastudios.sealcode.frontend;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

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

    public static String insertTextDialog(String title, String defaultText)
    {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle(title);
        dialog.setHeaderText(defaultText);
        dialog.setContentText("Nombre:");

        // Mostrar el diálogo y esperar a que el usuario ingrese el texto
        Optional<String> result = dialog.showAndWait();

        dialog.addEventHandler(KeyEvent.KEY_PRESSED, event ->
        {
            switch (event.getCode())
            {
                case ENTER:
                    dialog.close();
                    break;
                case ESCAPE:
                    dialog.close();
                    break;
            }
        });

        return result.orElse("");
    }

    public static String insertTextDialog(String title)
    {
       return insertTextDialog(title, "");
    }
}
