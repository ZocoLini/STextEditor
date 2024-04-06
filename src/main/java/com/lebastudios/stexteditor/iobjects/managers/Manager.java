package com.lebastudios.stexteditor.iobjects.managers;

import com.lebastudios.stexteditor.TextEditorApplication;
import javafx.stage.Stage;

public abstract class Manager<T>
{
    protected static final Stage stage = TextEditorApplication.getStage();
    protected T managedObject;
    
    public Manager(T managedObject)
    {
        System.out.println("Inicializacion de " + getClass().getSimpleName() + "...");
        this.managedObject = managedObject;
        
        TextEditorApplication.INSTANCIATED_MANAGERS.add(this);

        // Añadimos los event handlers que necesitará cada Mnager en específico
        addEventHandlers();
    }
    
    /**
     * Called since the object is being created. This is called after the start method.
     */
    protected void addEventHandlers() {}
}
