package com.lebastudios.sealcode.iobjects.managers.nodemanagers;

import com.lebastudios.sealcode.iobjects.managers.Manager;
import javafx.scene.Node;

public abstract class NodeManager<T extends Node> extends Manager<T>
{
    public NodeManager(T representingObject)
    {
        super(representingObject);
    }
}
