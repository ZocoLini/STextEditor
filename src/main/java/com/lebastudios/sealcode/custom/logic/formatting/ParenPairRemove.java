package com.lebastudios.sealcode.custom.logic.formatting;

import com.lebastudios.sealcode.events.IEventMethod3;
import com.lebastudios.sealcode.global.TextModInf;
import com.lebastudios.sealcode.core.frontend.fxextends.SealCodeArea;

import java.util.Map;

public class ParenPairRemove implements IEventMethod3<String, TextModInf, SealCodeArea>
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
