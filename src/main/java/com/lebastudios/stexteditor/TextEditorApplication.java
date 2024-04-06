package com.lebastudios.stexteditor;

import com.lebastudios.stexteditor.applogic.Resources;
import com.lebastudios.stexteditor.applogic.config.proyect.ProyectConfig;
import com.lebastudios.stexteditor.applogic.config.global.Session;
import com.lebastudios.stexteditor.applogic.config.global.GlobalConfig;
import com.lebastudios.stexteditor.iobjects.managers.Manager;
import com.lebastudios.stexteditor.events.GlobalEvents;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.MainManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextEditorApplication extends Application
{
    public static final List<Manager<?>> INSTANCIATED_MANAGERS = new ArrayList<>();

    private static TextEditorApplication instance;

    public static Stage getStage()
    {
        return instance.stage;
    }

    public TextEditorApplication()
    {
        instance = this;
    }

    private Stage stage;

    private String actualStyle = Resources.getThemeStyle();
    
    @Override
    public void start(Stage stage) throws IOException
    {
        this.stage = stage;
        
        Thread hiloPrecargaGlobalConfig = GlobalConfig.getStaticInstance().preload();
        Thread hiloPrecargaSession = Session.getStaticInstance().preload();
        Thread hiloPrecargaProyectConfig = ProyectConfig.getStaticInstance().preload();

        FXMLLoader fxmlLoader =
                new FXMLLoader(TextEditorApplication.class.getResource("hello-view.fxml"));

        Scene escenaActual = new Scene(fxmlLoader.load());
        
        stage.setScene(escenaActual);
        
        escenaActual.getStylesheets().add(actualStyle);
        
        stage.setTitle("Text Editor!");

        stage.addEventHandler(WindowEvent.WINDOW_HIDING, event -> GlobalConfig.getStaticInstance().save());
        stage.addEventHandler(WindowEvent.WINDOW_HIDING, event -> Session.getStaticInstance().save());
        GlobalEvents.onThemeChanged.addListener(this::setActualStyle);

        try
        {
            hiloPrecargaGlobalConfig.join();
            hiloPrecargaSession.join();
            hiloPrecargaProyectConfig.join();
        }
        catch (InterruptedException e)
        {
            System.out.println("Error al cargar la configuración. No se pude comenzar el programa");
            throw new RuntimeException(e);
        }

        stage.show();

        System.out.println("Aplicación iniciada.");
        
        MainManager.getInstance().load();
    }
    
    private void setActualStyle()
    {
        this.stage.getScene().getStylesheets().remove(actualStyle);
        actualStyle = Resources.getThemeStyle();
        this.stage.getScene().getStylesheets().add(actualStyle);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
    
}