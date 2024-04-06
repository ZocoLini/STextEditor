package com.lebastudios.stexteditor.iobjects.managers.nodemanagers.instanciablemanager;

import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.NodeManager;
import javafx.scene.Node;

public abstract class InstanciableManager<T extends Node> extends NodeManager<T>
{
    public InstanciableManager(T representingObject)
    {
        super(representingObject);
    }
}
