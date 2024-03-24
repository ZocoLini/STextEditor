package com.lebastudios.stexteditor.events;

/**
 * Abstract class for the event system. This class is used to create events that can be invoked without parameters.
 */
public abstract class AppEvent extends EventHandler<AppEvent.IEventMethod> 
{
    /**
     * Interface for the event method to be invoked without parameters.
     */
    public interface IEventMethod
    {
        void invoke();
    }

    public void invoke()
    {
        for (IEventMethod listener : listeners)
        {
            try
            {
                listener.invoke();
            }
            catch (Exception e)
            {
                System.err.println("Error invoking event: " + e.getMessage());
            }
        }
    }
}
