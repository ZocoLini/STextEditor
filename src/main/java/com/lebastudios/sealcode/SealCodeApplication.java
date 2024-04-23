package com.lebastudios.sealcode;

import com.lebastudios.sealcode.applogic.AppLoop;
import com.lebastudios.sealcode.applogic.config.GlobalConfig;
import com.lebastudios.sealcode.applogic.config.Session;
import com.lebastudios.sealcode.events.AppEvents;
import com.lebastudios.sealcode.frontend.stages.MainStage;
import com.lebastudios.sealcode.ideimplementation.SpecificIDEImplementations;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class SealCodeApplication extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        SpecificIDEImplementations.implementation();
        
        Thread hiloPrecargaGlobalConfig = GlobalConfig.getStaticInstance().preload();
        Thread hiloPrecargaSession = Session.getStaticInstance().preload();

        try
        {
            hiloPrecargaGlobalConfig.join();
            hiloPrecargaSession.join();
        }
        catch (InterruptedException e)
        {
            System.out.println("Error al cargar la configuración. No se pude comenzar el programa");
            throw new RuntimeException(e);
        }
        stage = MainStage.getInstance();

        stage.show();

        AppEvents.onAppStart.invoke();
        
        AppLoop.startLoop();
    }
}