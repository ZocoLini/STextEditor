package com.lebastudios.sealcode.custom.logic.formatting;

import com.lebastudios.sealcode.core.logic.config.GlobalConfig;
import com.lebastudios.sealcode.core.frontend.fxextends.SealCodeArea;
import com.lebastudios.sealcode.events.IEventMethod3;
import com.lebastudios.sealcode.global.TextModInf;

public class IndentNextLineInsert implements IEventMethod3<String, TextModInf, SealCodeArea>
{
    @Override
    public void invoke(String oldText, TextModInf modInf, SealCodeArea codeArea)
    {
        if (!modInf.textModificated.equals("\n")) return;
        
        // Remplaza los \n por \n + " " * indentación necesaria
        int actualInden = codeArea.getParagraphInden();
        int indentationNeeded = actualInden;
        String newText = modInf.textModificated;

        if (codeArea.getNoBlankPreviusChar(modInf.start).equals("{") && oldText.isEmpty())
        {
            indentationNeeded += GlobalConfig.getStaticInstance().editorConfig.indentation;
        }

        newText = newText.replace("\n", "\n" + " ".repeat(indentationNeeded));

        if (codeArea.getNoBlankNextChar(modInf.end).equals("}") 
                && codeArea.getNoBlankPreviusChar(modInf.start).equals("{") && oldText.isEmpty())
        {
            newText += "$END$\n" + " ".repeat(actualInden);
        }
        
        modInf.update(modInf.start, modInf.end, newText);
    }
}
