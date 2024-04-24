package com.lebastudios.sealcode.logic.formatting;

import com.lebastudios.sealcode.core.frontend.fxextends.SealCodeArea;
import com.lebastudios.sealcode.util.TextModInf;
import com.lebastudios.sealcode.events.ITextMod;
import com.lebastudios.sealcode.config.GlobalConfig;

public class TabRemover implements ITextMod
{
    @Override
    public void invoke(String oldText, TextModInf modInf, SealCodeArea codeArea)
    {
        String newText = modInf.textModificated
                .replace("\t", " ".repeat(GlobalConfig.getStaticInstance().editorConfig.tabSize));
        modInf.update(modInf.start, modInf.end, newText);
    }
}
