package com.lebastudios.sealcode.applogic.txtmod;

import com.lebastudios.sealcode.frontend.fxextends.SealCodeArea;

import java.util.Map;

public class ParenModifications implements ITextMod
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
    public TextModInf onTextInserted(String oldText, TextModInf modInf, SealCodeArea codeArea)
    {
        String nextChar = codeArea.getNextChar(modInf.end);
        String prevChar = codeArea.getPreviusChar(modInf.start);
        
        String newText = modInf.textModificated;
        
        // Autocompleta pares
        if (pairs.containsKey(newText))
        {
            newText += "$END$" + pairs.get(newText);
            return new TextModInf(modInf.start, modInf.end, newText, -1);
        }

        // Salta el caret al intentar completar un par ya completo
        if (newText.equals(nextChar) && nextChar.equals(pairs.getOrDefault(prevChar, "")))
        {
            return new TextModInf(modInf.start, modInf.end, "", modInf.start + 1);
        }
        
        return modInf;
    }

    @Override
    public TextModInf onTextDeleted(String oldText, TextModInf modInf, SealCodeArea codeArea)
    {
        // Autoelimina pares
        if (pairs.containsKey(oldText) && codeArea.getNextChar(modInf.end).equals(pairs.get(oldText)))
        {
            return new TextModInf(modInf.start, modInf.end + 1, "", -1);
        }

        return modInf;
    }
}
