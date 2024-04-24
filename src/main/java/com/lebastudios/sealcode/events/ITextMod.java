package com.lebastudios.sealcode.events;

import com.lebastudios.sealcode.util.TextModInf;
import com.lebastudios.sealcode.core.frontend.fxextends.SealCodeArea;

public interface ITextMod
{
    void invoke(String oldText, TextModInf modInf, SealCodeArea codeArea);
}
