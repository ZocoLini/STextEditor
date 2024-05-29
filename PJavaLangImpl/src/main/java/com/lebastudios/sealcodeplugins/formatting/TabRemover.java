package com.lebastudios.sealcodeplugins.formatting;

import com.lebastudios.sealcode.frontend.SealCodeArea;
import com.lebastudios.sealcode.events.IEventMethod3;
import com.lebastudios.sealcode.TextModInf;
import com.lebastudios.sealcode.config.GlobalConfig;

public class TabRemover implements IEventMethod3<String, TextModInf, SealCodeArea>
{
    @Override
    public void invoke(String oldText, TextModInf modInf, SealCodeArea codeArea)
    {
        String newText = modInf.textModificated
                .replace("\t", " ".repeat(GlobalConfig.getStaticInstance().editorConfig.tabSize));
        modInf.update(modInf.start, modInf.end, newText);
    }
}
