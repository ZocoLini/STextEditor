package com.lebastudios.sealcode.events;

import com.lebastudios.sealcode.util.TextModInf;
import com.lebastudios.sealcode.core.frontend.fxextends.SealCodeArea;

import java.util.List;

public abstract class TextModEvent extends EventMapHandler<String, ITextMod>
{
    public void invoke(String key, String oldText, TextModInf modInf, SealCodeArea codeArea)
    {
        // Esta copia permite llamar a todos los listeners aunque se modifique la lista durante una llamada
        List<ITextMod> listaAuxiliar = getListenersFromKey(key).stream().toList();

        for (ITextMod listener : listaAuxiliar)
        {
            try
            {
                listener.invoke(oldText, modInf, codeArea);
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
