package com.lebastudios.sealcode.applogic.txtmod;

import com.lebastudios.sealcode.frontend.fxextends.SealCodeArea;

import java.util.Map;

public class TextModParen
{
    Map<String, String> pairs = Map.of(
            "(", ")",
            "{", "}",
            "[", "]",
            "\"", "\"",
            "'", "'",
            "<", ">"
    );
    
    public TextModParen(SealCodeArea codeArea)
    {

        codeArea.onTextInserted.addListener((start, end, oldText, newText) ->
        {
            if (pairs.containsKey(newText))
            {
                newText += "$END$" + pairs.get(newText);
            }

            return newText;
        });
        
        codeArea.onTextRemoved.addListener((start, end, oldText, newText) ->
        {
            if (pairs.containsKey(oldText) && codeArea.getNextChar(end).equals(pairs.get(oldText)))
            {
                newText = "";
            }

            return newText;
        });
        
        codeArea.onTextInserted.addListener((start, end, oldText, newText) ->
        {
            String nextChar = codeArea.getNextChar(end);
            String prevChar = codeArea.getPreviusChar(start);

            if (newText.equals(nextChar) && nextChar.equals(pairs.getOrDefault(prevChar, "")))
            {
                newText = "";
                codeArea.moveTo(start + 1);
            }

            return newText;
        });
    }
}
