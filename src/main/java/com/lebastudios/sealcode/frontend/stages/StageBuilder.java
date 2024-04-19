package com.lebastudios.sealcode.frontend.stages;

import com.lebastudios.sealcode.SealCodeApplication;
import com.lebastudios.sealcode.applogic.Resources;
import com.lebastudios.sealcode.applogic.events.AppEvents;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class StageBuilder extends Stage
{
    public StageBuilder(String resourceName, String title)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(SealCodeApplication.class.getResource(resourceName));

        Scene escenaActual;

        try
        {
            escenaActual = new Scene(fxmlLoader.load());
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        this.setScene(escenaActual);

        escenaActual.getStylesheets().add(Resources.getThemeStyle());

        AppEvents.onThemeChange.addListener(() ->
        {
            escenaActual.getStylesheets().clear();
            escenaActual.getStylesheets().add(Resources.getThemeStyle());
        });

        this.setTitle(title);

        addEventHandlers();
    }

    protected void addEventHandlers()
    {
    }
}
