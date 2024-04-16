package com.lebastudios.sealcode.iobjects.stages.main;

import com.lebastudios.sealcode.SealCodeApplication;
import com.lebastudios.sealcode.applogic.Resources;
import com.lebastudios.sealcode.applogic.config.GlobalConfig;
import com.lebastudios.sealcode.applogic.config.Session;
import com.lebastudios.sealcode.applogic.events.AppEvents;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class MainStage extends Stage
{
    private static MainStage instance;
    
    public static MainStage getInstance()
    {
        if (instance == null) instance = new MainStage();
        
        return instance;
    }
    
    private MainStage()
    {
        super();

        FXMLLoader fxmlLoader =
                new FXMLLoader(SealCodeApplication.class.getResource("sealCodeMainScene.fxml"));

        Scene escenaActual;
        
        try
        {
            escenaActual = new Scene(fxmlLoader.load());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        this.setScene(escenaActual);

        escenaActual.getStylesheets().add(Resources.getThemeStyle());

        this.setTitle("Text Editor!");

        this.addEventHandler(WindowEvent.WINDOW_HIDING, event -> GlobalConfig.getStaticInstance().save());
        this.addEventHandler(WindowEvent.WINDOW_HIDING, event -> Session.getStaticInstance().save());
        this.addEventHandler(WindowEvent.WINDOW_HIDING, event -> AppEvents.ON_APP_EXIT.invoke());
    }
}
