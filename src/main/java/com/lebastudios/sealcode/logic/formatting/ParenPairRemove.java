package com.lebastudios.sealcode.logic.formatting;

import com.lebastudios.sealcode.util.TextModInf;
import com.lebastudios.sealcode.events.ITextMod;
import com.lebastudios.sealcode.core.frontend.fxextends.SealCodeArea;

import java.util.Map;

public class ParenPairRemove implements ITextMod
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
        if (pairs.containsKey(oldText) && codeArea.getNextChar(modInf.end).equals(pairs.get(oldText)))
        {
            modInf.update(modInf.start, modInf.end + 1, "");
        }
    }
}
