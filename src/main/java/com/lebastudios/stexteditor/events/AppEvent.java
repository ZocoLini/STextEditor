package com.lebastudios.stexteditor.events;

import java.util.ArrayList;
import java.util.List;

public abstract class AppEvent
{
    public interface IEvent
    {
        void invoke();
    }
    
    protected final List<IEvent> listeners = new ArrayList<>();
    
    public void addListener(IEvent listener)
    {
        listeners.add(listener);
    }
    
    public void removeListener(IEvent listener)
    {
        listeners.remove(listener);
    }
    
    public void invoke()
    {
        for (IEvent listener : listeners)
        {
            try
            {
                listener.invoke();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public void clearListeners()
    {
        listeners.clear();
    }
    
    public List<IEvent> getListeners()
    {
        return listeners;
    }
    
    public boolean hasListener(IEvent listener)
    {
        return listeners.contains(listener);
    }
}
