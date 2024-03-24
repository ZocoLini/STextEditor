package com.lebastudios.stexteditor.controllers;

import com.lebastudios.stexteditor.TextEditorApplication;
import com.lebastudios.stexteditor.events.Events;

public abstract class Controller
{
    public Controller()
    {
        TextEditorApplication.instanciatedControllers.add(this);

        awake();
        start();
        
        Events.onUpdate.addListener(this::update);
    }
    
    protected void start() {}
    protected void update() {}
    protected void awake() {}
}
