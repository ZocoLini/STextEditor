package com.lebastudios.sealcode.events;

import java.util.List;

public class AppEvent1<T> extends EventHandler<IEventMethod1<T>>
{
    public void invoke(T t)
    {
        // Esta copia permite llamar a todos los listeners aunque se modifique la lista durante una llamada
        List<IEventMethod1<T>> listaAuxiliar = listeners.stream().toList();

        for (IEventMethod1<T> listener : listaAuxiliar)
        {
            try
            {
                listener.invoke(t);
            }
            catch (Exception e)
            {
                System.err.println("Error invoking event: " + e.getMessage());
            }
        }
    }
}
