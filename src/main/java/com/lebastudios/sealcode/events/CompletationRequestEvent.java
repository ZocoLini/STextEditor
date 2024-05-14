package com.lebastudios.sealcode.events;

import com.lebastudios.sealcode.core.logic.Completation;

import java.io.File;
import java.util.List;
import java.util.TreeSet;

public abstract class CompletationRequestEvent extends EventHandler<ICompletationsRequest>
{
    public void invoke(TreeSet<Completation> newCompletations, File file, String fileExtension,
                       String lastWord, String text)
    {
        // Esta copia permite llamar a todos los listeners aunque se modifique la lista durante una llamada
        List<ICompletationsRequest> listaAuxiliar = listeners.stream().toList();

        for (ICompletationsRequest listener : listaAuxiliar)
        {
            try
            {
                listener.invoke(newCompletations, file, fileExtension, lastWord, text);
            }
            catch (Exception e)
            {
                System.err.println("Error invoking event: " + e.getMessage());
            }
        }
    }
}
