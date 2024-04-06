package com.lebastudios.stexteditor.iobjects.managers.objectmanagers;

import com.lebastudios.stexteditor.iobjects.managers.Manager;

public abstract class ObjectManager<T> extends Manager<T>
{
    public ObjectManager(T representingObject)
    {
        super(representingObject);
    }
}
