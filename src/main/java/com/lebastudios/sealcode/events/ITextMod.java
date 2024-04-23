package com.lebastudios.sealcode.events;

import com.lebastudios.sealcode.ideimplementation.txtmod.TextModInf;
import com.lebastudios.sealcode.frontend.fxextends.SealCodeArea;

public interface ITextMod
{
    default void invoke(String oldText, TextModInf modInf, SealCodeArea codeArea) {}
}
