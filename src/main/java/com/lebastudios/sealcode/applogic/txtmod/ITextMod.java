package com.lebastudios.sealcode.applogic.txtmod;

import com.lebastudios.sealcode.frontend.fxextends.SealCodeArea;

public interface ITextMod
{
    default TextModInf onTextInserted(String oldText, final TextModInf modInf,
                                      SealCodeArea codeArea)
    {
        return modInf;
    }

    default TextModInf onTextDeleted(String oldText, final TextModInf modInf,
                                     SealCodeArea codeArea)
    {
        return modInf;
    }

    default TextModInf onTextReplaced(String oldText, final TextModInf modInf,
                                      SealCodeArea codeArea)
    {
        return modInf;
    }
    
    default TextModInf onTextModification(String oldText, final TextModInf modInf,
                                          SealCodeArea codeArea)
    {
        return modInf;
    }
}
