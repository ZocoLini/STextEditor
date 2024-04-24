package com.lebastudios.sealcode.core.frontend.fxextends;

import com.lebastudios.sealcode.util.MessageType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public final class Notification extends VBox
{

    public Notification(String message)
    {
        this(message, MessageType.Info);
    }

    public Notification(String message, MessageType msgType)
    {
        super();
        
        // Establecer el estilo del cuadro de notificación
        setPadding(new Insets(10));

        // Agregar un icono
        Circle icon = new Circle(5, switch (msgType)
        {
            case MessageType.Info -> Color.GREEN;
            case MessageType.Warning -> Color.ORANGE;
            case MessageType.Error -> Color.RED;
        });
        icon.setStroke(Color.WHITE);
        HBox.setMargin(icon, new Insets(0, 10, 0, 0));

        // Agregar el texto de la notificación
        Label text = new Label(message);
        HBox.setHgrow(text, Priority.ALWAYS);

        // Agregar un botón de eliminar
        Button deleteButton = new Button();
        deleteButton.setBackground(
                new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(20), Insets.EMPTY)));
        
        deleteButton.setGraphic(new IconView("x.png"));

        deleteButton.setOnAction(event -> ((Pane) getParent()).getChildren().remove(this));

        // Colocar elementos en un HBox
        HBox buttonContainer = new HBox(deleteButton);
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(buttonContainer, Priority.ALWAYS);

        HBox hbox = new HBox(icon, text, buttonContainer);
        hbox.setAlignment(Pos.CENTER_LEFT);

        // Agregar el HBox al cuadro de notificación
        getChildren().add(hbox);

        HBox.setHgrow(this, Priority.ALWAYS);
    }
}
