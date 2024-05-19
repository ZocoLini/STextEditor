package com.lebastudios.sealcode.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MappedEventHandler<K, T>
{
    protected final Map<K, List<T>> listenersMap = new HashMap<>();

    /**
     * Añade un listener a la key especificada
     *
     * @param key clave donde insertar
     * @param listener listener a insertar
     */
    public void addListener(K key, T listener)
    {
        List<T> listeners = listenersMap.computeIfAbsent(key, k -> new ArrayList<>());
        listeners.add(listener);
    }

    /**
     * Elimina todos los listeners asicionados con la key pasada como parámetro
     *
     * @param key Identificaador de los metodos a ser eliminados
     */
    public void removeListenersFromKey(K key)
    {
        listenersMap.remove(key);
    }

    public void removeListenerFromKey(K key, T listener)
    {
        List<T> listeners = listenersMap.get(key);

        if (listeners == null) return;

        listeners.remove(listener);
    }

    public void clearListenersMap()
    {
        listenersMap.clear();
    }

    public List<T> getListenersFromKey(K clave)
    {
        return listenersMap.getOrDefault(clave, new ArrayList<>());
    }

    public boolean hasListenerInKey(K key, T listener)
    {
        return listenersMap.getOrDefault(key, new ArrayList<>()).contains(listener);
    }
}
