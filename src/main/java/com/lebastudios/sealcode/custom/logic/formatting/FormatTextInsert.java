package com.lebastudios.sealcode.custom.logic.formatting;

import com.lebastudios.sealcode.core.frontend.fxextends.SealCodeArea;
import com.lebastudios.sealcode.core.logic.config.GlobalConfig;
import com.lebastudios.sealcode.events.IEventMethod3;
import com.lebastudios.sealcode.global.TextModInf;

import java.util.List;
import java.util.TreeSet;

public class FormatTextInsert implements IEventMethod3<String, TextModInf, SealCodeArea>
{
    TreeSet<Character> charsThatIncreeseInden = new TreeSet<>(List.of(
            '{'
    ));
    TreeSet<Character> charsThatDecreseInden = new TreeSet<>(List.of(
            '}'
    ));
    
    @Override
    public void invoke(String oldText, TextModInf modInf, SealCodeArea codeArea)
    {
        String[] newTextLines = modInf.textModificated.trim().split("\n");

        if (newTextLines.length == 1) return;
        
        StringBuilder newText = new StringBuilder();
        
        String startingIndent = " ".repeat(codeArea.getParagraphInden());
        
        int indenCount = 0;
        for (int i = 0; i < newTextLines.length; i++)
        {
            String variable = newTextLines[i];
            
            if (variable.isEmpty()) 
            {
                return;
            }
            
            if (!variable.isBlank()) 
            {
                variable = variable.trim();
            }

            if (aumentaIndentacion(variable))
            {
                indenCount++;
            }
            else if (i + 1 < newTextLines.length && disminuyeIndentacion(newTextLines[i + 1])) 
            {
                indenCount--;
                
                if (indenCount < 0) 
                {
                    indenCount = 0;
                }
            }
            
            newText.append(variable).append("\n");
            newText.append(" ".repeat(indenCount * GlobalConfig.getStaticInstance().editorConfig.indentation));
            newText.append(startingIndent);
        }
        
        modInf.update(newText.substring(0, newText.lastIndexOf("\n")));
    }
    
    private boolean aumentaIndentacion(String texto)
    {
        return texto.endsWith("{");
    }
    
    private boolean disminuyeIndentacion(String texto)
    {
        return texto.endsWith("}");
    }
}
