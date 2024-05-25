package com.lebastudios.sealcode;

import com.lebastudios.sealcode.core.logic.PluginLoader;
import com.lebastudios.sealcode.core.logic.config.GlobalConfig;
import com.lebastudios.sealcode.core.logic.config.Session;
import com.lebastudios.sealcode.events.AppEvents;
import com.lebastudios.sealcode.core.frontend.stages.MainStage;
import com.lebastudios.sealcode.custom.CustomStarter;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public final class SealCodeApplication extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        System.setProperty("java.util.jar.disableVerification", "true");
        PluginLoader.loadPlugins();
        
        CustomStarter.startCustomImplementation();
        
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
    }
}