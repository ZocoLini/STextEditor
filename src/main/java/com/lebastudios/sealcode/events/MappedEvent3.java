package com.lebastudios.sealcode.events;

import java.util.ArrayList;
import java.util.List;

public class MappedEvent3<K, A, B, C> extends MappedEventHandler<K, IEventMethod3<A, B, C>>
{
    public void invoke(K key, A a, B b, C c)
    {
        // Esta copia permite llamar a todos los listeners aunque se modifique la lista durante una llamada
        List<IEventMethod3<A, B, C>> listaAuxiliar = new ArrayList<>(getListenersFromKey(key));

        for (var listener : listaAuxiliar)
        {
            try
            {
                listener.invoke(a, b, c);
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
