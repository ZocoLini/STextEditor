package com.lebastudios.sealcode.events;

import org.fxmisc.richtext.model.StyleSpans;

import java.util.Collection;
import java.util.List;

public abstract class StyleSpansRequestEvent extends EventHandler<IStyleSpansRequest>
{
    public void invoke(StyleSpans<Collection<String>> styleSpans, String text, String filExtension)
    {
        // Esta copia permite llamar a todos los listeners aunque se modifique la lista durante una llamada
        List<IStyleSpansRequest> listaAuxiliar = listeners.stream().toList();

        for (IStyleSpansRequest listener : listaAuxiliar)
        {
            try
            {
                listener.invoke(styleSpans, text, filExtension);
            }
            catch (Exception e)
            {
                System.err.println("Error invoking event: " + e.getMessage());
            }
        }
    }
}
