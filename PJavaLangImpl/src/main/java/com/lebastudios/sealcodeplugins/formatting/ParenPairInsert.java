package com.lebastudios.sealcodeplugins.formatting;

import com.lebastudios.sealcode.events.IEventMethod3;
import com.lebastudios.sealcode.TextModInf;
import com.lebastudios.sealcode.frontend.SealCodeArea;

import java.util.Map;

public class ParenPairInsert implements IEventMethod3<String, TextModInf, SealCodeArea>
{
    private static final Map<String, String> pairs = Map.of(
            "(", ")",
            "{", "}",
            "[", "]",
            "\"", "\"",
            "'", "'",
            "<", ">"
    );
    
    @Override
    public void invoke(String oldText, TextModInf modInf, SealCodeArea codeArea)
    {
        String nextChar = codeArea.getNextChar(modInf.end);
        String prevChar = codeArea.getPreviusChar(modInf.start);
        
        String newText = modInf.textModificated;
        
        // Autocompleta pares
        if (pairs.containsKey(newText))
        {
            newText += "$END$" + pairs.get(newText);
            modInf.update(modInf.start, modInf.end, newText);
            return;
        }

        // Salta el caret al intentar completar un par ya completo
        if (newText.equals(nextChar) && nextChar.equals(pairs.getOrDefault(prevChar, "")))
        {
            modInf.update(modInf.start, modInf.end, "", modInf.start + 1);
            return;
        }
    }
}
