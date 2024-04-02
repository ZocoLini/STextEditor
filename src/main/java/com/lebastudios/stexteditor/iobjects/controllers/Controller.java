package com.lebastudios.stexteditor.iobjects.controllers;

import com.lebastudios.stexteditor.TextEditorApplication;
import com.lebastudios.stexteditor.events.GlobalEvents;
import javafx.stage.Stage;

public abstract class Controller
{
    protected static final Stage stage = TextEditorApplication.getStage();
    
    public Controller()
    {
        TextEditorApplication.instanciatedControllers.add(this);

        awake();
        start();
        
        addEventHandlers();
        
        GlobalEvents.onUpdate.addListener(this::update);
    }

    /**
     * Called when the controller is created. This is called before the start method. This is called once.
     */
    protected void awake() {}

    /**
     * Called when the controller is created. This is called after the awake method. This is called once.
     */
    protected void start() {}

    /**
     * Called every "frame". This called after the start method.
     */
    protected void update() {}

    /**
     * Called since the object is being created. This is called after the start method.
     */
    protected void addEventHandlers() {}
}
