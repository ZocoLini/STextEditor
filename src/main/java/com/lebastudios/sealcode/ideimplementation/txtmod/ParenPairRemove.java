package com.lebastudios.sealcode.ideimplementation.txtmod;

import com.lebastudios.sealcode.events.ITextMod;
import com.lebastudios.sealcode.frontend.fxextends.SealCodeArea;

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
