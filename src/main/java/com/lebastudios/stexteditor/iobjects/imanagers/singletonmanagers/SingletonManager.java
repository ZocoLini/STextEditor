package com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers;

import com.lebastudios.stexteditor.TextEditorApplication;
import com.lebastudios.stexteditor.iobjects.imanagers.Manager;
import javafx.scene.Node;
import javafx.stage.Stage;

public abstract class SingletonManager<T extends Node> extends Manager<T>
{
    protected static final Stage stage = TextEditorApplication.getStage();
    
    public SingletonManager(T representingNode)
    {
        super(representingNode);
    }

    public void load()
    {
        loadShelf();
        loadChilds();
    }
    
    protected void loadShelf() {}
    
    protected abstract void loadChilds();
}
