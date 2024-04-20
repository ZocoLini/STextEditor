package com.lebastudios.sealcode.frontend.stages;

import com.lebastudios.sealcode.applogic.config.GlobalConfig;
import com.lebastudios.sealcode.applogic.config.Session;
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
        this.addEventHandler(WindowEvent.WINDOW_HIDING, event -> GlobalConfig.getStaticInstance().save());
        this.addEventHandler(WindowEvent.WINDOW_HIDING, event -> Session.getStaticInstance().save());
        this.addEventHandler(WindowEvent.WINDOW_HIDING, event -> AppEvents.onAppExit.invoke());
    }

    public static MainStage getInstance()
    {
        if (instance == null) instance = new MainStage();

        return instance;
    }
}
