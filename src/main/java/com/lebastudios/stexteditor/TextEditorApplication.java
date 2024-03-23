package com.lebastudios.stexteditor;

import com.lebastudios.stexteditor.app.config.Session;
import com.lebastudios.stexteditor.app.config.Config;
import com.lebastudios.stexteditor.controllers.TabPaneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class TextEditorApplication extends Application {
    
    public Scene escenaActual;
    
    @Override
    public void start(Stage stage) throws IOException {
        Thread hiloPrecargaConfig = Config.preload();
        Thread hiloPrecargaSession = Session.preload();
        
        FXMLLoader fxmlLoader = 
                new FXMLLoader(TextEditorApplication.class.getResource("hello-view.fxml"));
        
        escenaActual = new Scene(fxmlLoader.load());
       
        stage.setTitle("Text Editor!");
        
        stage.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> TabPaneController.getInstance().loadLastFiles());
        stage.addEventHandler(WindowEvent.WINDOW_HIDING, event -> Config.getInstance().save());
        stage.addEventHandler(WindowEvent.WINDOW_HIDING, event -> Session.getInstance().save());
        
        stage.setScene(escenaActual);

        try
        {
            hiloPrecargaConfig.join();
            hiloPrecargaSession.join();
        }
        catch (InterruptedException e)
        {
            System.out.println("Error al cargar la configuración. No se pude comenzar el programa");
            throw new RuntimeException(e);
        }

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}