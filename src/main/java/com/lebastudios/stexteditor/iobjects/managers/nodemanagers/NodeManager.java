package com.lebastudios.stexteditor.iobjects.managers.nodemanagers;

import com.lebastudios.stexteditor.iobjects.managers.Manager;
import javafx.scene.Node;

public abstract class NodeManager<T extends Node> extends Manager<T>
{
    public NodeManager(T representingObject)
    {
        super(representingObject);
    }
}
