package com.lebastudios.sealcode.iobjects.fxextends;

import com.lebastudios.sealcode.iobjects.stages.main.MainStageController;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class NotificationsContainer extends BorderPane
{
    private final VBox notiContainer;
    
    public NotificationsContainer()
    {
        super(MainStageController.getInstance().notificationsContainer);

        notiContainer = new VBox(5);
        notiContainer.setPadding(new Insets(20));

        ScrollPane scrollPane = new ScrollPane(notiContainer);
        scrollPane.setFitToWidth(true);

        this.setCenter(scrollPane);
    }

    public void addNotification(Notification notification)
    {
        notiContainer.getChildren().add(notification);
    }
}
