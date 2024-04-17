package com.lebastudios.sealcode.frontend.stages.settings;

import com.lebastudios.sealcode.frontend.stages.StageBuilder;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

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

        this.addEventHandler(KeyEvent.KEY_PRESSED, event ->
        {
            SettingsStageController.getInstance().loadNewSettingsPane("themeAnchorPane.fxml");
        });
    }

    public static SettingsStage getInstance()
    {
        if (instance == null) instance = new SettingsStage();

        return instance;
    }
}
