package com.lebastudios.stexteditor.iobjects.imanagers.instanciablemanager;

import com.lebastudios.stexteditor.iobjects.imanagers.Manager;
import javafx.scene.Node;

public abstract class InstanciableInstanciableManager<T extends Node> extends Manager<T>
{
    public InstanciableInstanciableManager(T representingObject)
    {
        super(representingObject);
    }
}
