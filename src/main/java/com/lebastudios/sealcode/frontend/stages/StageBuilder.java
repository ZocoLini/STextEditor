package com.lebastudios.sealcode.frontend.stages;

import com.lebastudios.sealcode.SealCodeApplication;
import com.lebastudios.sealcode.controllers.IStageController;
import com.lebastudios.sealcode.config.Resources;
import com.lebastudios.sealcode.events.AppEvents;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class StageBuilder extends Stage
{
    protected IStageController controller;
    
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

        AppEvents.onGlobalConfigUpdate.addListener(() ->
        {
            escenaActual.getStylesheets().clear();
            escenaActual.getStylesheets().add(Resources.getThemeStyle());
        });

        this.setTitle(title);

        addEventHandlers();

        controller = fxmlLoader.getController();
        controller.customiceStage();
    }

    protected void addEventHandlers()
    {
    }
}
