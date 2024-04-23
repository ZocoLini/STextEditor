package com.lebastudios.sealcode.applogic.txtmod;

import com.lebastudios.sealcode.applogic.config.GlobalConfig;
import com.lebastudios.sealcode.frontend.fxextends.SealCodeArea;

public class Indent implements ITextMod
{
    @Override
    public TextModInf onTextInserted(String oldText, TextModInf modInf, SealCodeArea codeArea)
    {
        // Remplaza los \n por \n + " " * indentación necesaria
        int actualIndentation = codeArea.getParagraphIndentation();
        int indentationNeeded = actualIndentation;
        String newText = modInf.textModificated;

        if (codeArea.getNoBlankPreviusChar(modInf.start).equals("{") && oldText.isEmpty())
        {
            indentationNeeded += GlobalConfig.getStaticInstance().editorConfig.indentation;
        }

        newText = newText.replace("\n", "\n" + " ".repeat(indentationNeeded));

        if (codeArea.getNoBlankNextChar(modInf.end).equals("}") 
                && codeArea.getNoBlankPreviusChar(modInf.start).equals("{") && oldText.isEmpty())
        {
            newText += "$END$\n" + " ".repeat(actualIndentation);
        }
        
        return new TextModInf(modInf.start, modInf.end, newText);
    }
}
