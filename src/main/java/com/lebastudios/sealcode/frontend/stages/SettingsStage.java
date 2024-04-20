package com.lebastudios.sealcode.frontend.stages;

import com.lebastudios.sealcode.events.AppEvents;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;

public class SettingsStage extends StageBuilder
{
    private static SettingsStage instance;

    private SettingsStage()
    {
        super("settingsScene.fxml", "Settings");
        
        this.initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    protected void addEventHandlers()
    {
        this.addEventHandler(KeyEvent.KEY_PRESSED, event -> 
        {
            if (event.getCode() == KeyCode.ESCAPE) 
            {
                this.close();
            }
        });
        
        // TODO parece no funcionar el guardado de las settings.
        this.addEventHandler(WindowEvent.WINDOW_HIDING, event -> AppEvents.onSettingsUpdate.invoke());
    }

    public static SettingsStage getInstance()
    {
        if (instance == null) instance = new SettingsStage();

        return instance;
    }
}
