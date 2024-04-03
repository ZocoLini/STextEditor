package com.lebastudios.stexteditor.iobjects.controllers;

import com.lebastudios.stexteditor.TextEditorApplication;
import com.lebastudios.stexteditor.events.GlobalEvents;
import javafx.stage.Stage;

public abstract class Controller
{
    protected volatile boolean instanciated = false;
    
    public Controller()
    {
        TextEditorApplication.instanciatedControllers.add(this);

        awake();
        
        // Nos suscribimos a los eventos que _todo controller debe monitorear
        GlobalEvents.onUpdate.addListener(this::update);
        GlobalEvents.onThemeChanged.addListener(this::onThemeChangue);
        
        new Thread(this::ejecucionPostInicializacion).start();
    }

    private void ejecucionPostInicializacion()
    {
        long maxSecsWaiting = 1000 * 60;
        long actualSecond = System.currentTimeMillis();
        
        while (!instanciated) 
        {
            if (System.currentTimeMillis() - actualSecond > maxSecsWaiting) {
                System.err.println("Controller not marked as instanciated. " + getClass().getSimpleName() + " should " +
                        "add instanciated = true; in the constructor.");
                return;
            }
            
            Thread.onSpinWait();
        }
        
        start();
        
        // Añadimos los event handlers que necesitará cada Controller en específico
        addEventHandlers();
        
        // llamamos a aquellos métodos que necesiten una ejecución a la hora de crear el controller
        onThemeChangue();
    }
    
    /**
     * Called while the object is beeing instanciated. This is called before the start method. This is called once.
     */
    protected void awake() {}

    /**
     * Called when the controller is instanciated. This is called after the awake method. This is called once.
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

    /**
     * Called when the theme is changued.
     */
    protected void onThemeChangue() {}
}
