package com.lebastudios.sealcode.applogic.events;

import java.util.List;

/**
 * Abstract class for the event system. This class is used to create events that can be invoked without parameters.
 */
public abstract class AppEvent extends EventHandler<IEventMethod>
{
    public void invoke()
    {
        // Esta copia permite llamar a todos los listeners aunque se modifique la lista durante una llamada
        List<IEventMethod> listaAuxiliar = listeners.stream().toList();

        for (IEventMethod listener : listaAuxiliar)
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
