package com.lebastudios.sealcode.applogic.events;

import java.util.List;

public abstract class AppMapEvent<K> extends EventMapHandler<K, IEventMethod>
{
    public void invoke(K key)
    {
        // Esta copia permite llamar a todos los listeners aunque se modifique la lista durante una llamada
        List<IEventMethod> listaAuxiliar = getListenersFromKey(key).stream().toList();

        for (IEventMethod listener : listaAuxiliar)
        {
            try
            {
                listener.invoke();
            }
            catch (Exception e)
            {
                System.err.println("An error ocurred invoking an event, it will bre removed.");
                removeListenerFromKey(key, listener);
            }
        }
    }
}
