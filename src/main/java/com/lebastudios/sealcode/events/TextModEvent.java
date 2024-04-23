package com.lebastudios.sealcode.events;

import com.lebastudios.sealcode.ideimplementation.txtmod.TextModInf;
import com.lebastudios.sealcode.frontend.fxextends.SealCodeArea;

import java.util.List;

public abstract class TextModEvent extends EventHandler<ITextMod>
{
    public void invoke(String oldText, TextModInf modInf, SealCodeArea codeArea)
    {
        // Esta copia permite llamar a todos los listeners aunque se modifique la lista durante una llamada
        List<ITextMod> listaAuxiliar = listeners.stream().toList();

        for (ITextMod listener : listaAuxiliar)
        {
            try
            {
                listener.invoke(oldText, modInf, codeArea);
            } catch (Exception e)
            {
                System.err.println("Error invoking event: " + e.getMessage());
            }
        }
    }
}
