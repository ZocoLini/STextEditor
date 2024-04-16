package com.lebastudios.sealcode.iobjects.managers.nodemanagers.singletonmanagers;

import com.lebastudios.sealcode.iobjects.managers.nodemanagers.NodeManager;
import javafx.scene.Node;

public abstract class SingletonManager<T extends Node> extends NodeManager<T>
{
    public SingletonManager(T representingNode)
    {
        super(representingNode);
    }

    public void load()
    {
        loadChilds();
    }
   
    protected abstract void loadChilds();
}
