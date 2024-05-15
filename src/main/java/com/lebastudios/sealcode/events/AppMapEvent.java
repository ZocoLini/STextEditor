package com.lebastudios.sealcode.events;

import java.util.ArrayList;
import java.util.List;

public class AppMapEvent<K> extends EventMapHandler<K, IEventMethod>
{
    public void invoke(K key)
    {
        // Esta copia permite llamar a todos los listeners aunque se modifique la lista durante una llamada
        List<IEventMethod> listaAuxiliar = new ArrayList<>(getListenersFromKey(key));

        for (IEventMethod listener : listaAuxiliar)
        {
            try
            {
                listener.invoke();
            }
            catch (Exception e)
            {
                System.err.println("An error ocurred invoking an event, it will bre removed.");
                e.printStackTrace();
                removeListenerFromKey(key, listener);
            }
        }
    }
}
