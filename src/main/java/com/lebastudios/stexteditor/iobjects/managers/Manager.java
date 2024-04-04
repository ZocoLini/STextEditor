package com.lebastudios.stexteditor.iobjects.managers;

import com.lebastudios.stexteditor.TextEditorApplication;
import com.lebastudios.stexteditor.iobjects.controllers.Controller;
import javafx.scene.Node;
import javafx.stage.Stage;

public abstract class Manager<T extends Node> extends Controller<T>
{
    protected static final Stage stage = TextEditorApplication.getStage();
    
    public Manager(T representingNode)
    {
        super(representingNode);
    }
}
