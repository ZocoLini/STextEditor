package com.lebastudios.stexteditor.iobjects.managers;

import com.lebastudios.stexteditor.TextEditorApplication;
import com.lebastudios.stexteditor.iobjects.controllers.Controller;
import javafx.scene.Node;
import javafx.stage.Stage;

public abstract class Manager<T extends Node> extends Controller
{
    protected static final Stage stage = TextEditorApplication.getStage();
    
    protected T representingNode;
    
    public Manager(T representingNode)
    {
        super();
        
        this.representingNode = representingNode;
        
        instanciated = true;
    }
}
