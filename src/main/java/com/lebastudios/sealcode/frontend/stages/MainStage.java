package com.lebastudios.sealcode.frontend.stages;

import com.lebastudios.sealcode.config.GlobalConfig;
import com.lebastudios.sealcode.config.Session;
import com.lebastudios.sealcode.events.AppEvents;
import javafx.stage.WindowEvent;

public class MainStage extends StageBuilder
{
    private static MainStage instance;

    private MainStage()
    {
        super("sealCodeMainScene.fxml", "Seal Code");
    }

    @Override
    protected void addEventHandlers()
    {
        this.addEventHandler(WindowEvent.WINDOW_HIDING, event -> AppEvents.onAppExit.invoke());
        AppEvents.onAppExit.addListener(() -> GlobalConfig.getStaticInstance().save());
        AppEvents.onAppExit.addListener(() -> Session.getStaticInstance().save());
    }

    public static MainStage getInstance()
    {
        if (instance == null) instance = new MainStage();

        return instance;
    }
}
