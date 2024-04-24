package com.lebastudios.sealcode.logic.formatting;

import com.lebastudios.sealcode.util.TextModInf;
import com.lebastudios.sealcode.events.ITextMod;
import com.lebastudios.sealcode.core.frontend.fxextends.SealCodeArea;

public class JumpBlankLines implements ITextMod
{
    @Override
    public void invoke(String oldText, TextModInf modInf, SealCodeArea codeArea)
    {
        // Cuando se borra y el paragrafo solo son espacios, se borra_todo el paragrafo
        final var currentParagraph = codeArea.getCurrentParagraph();
        
        
        
        if (oldText.equals(" "))
        {
            if (modInf.start == codeArea.getCaretPosition()) 
            {
                return;
            }
            
            int paragraphStart = codeArea.paragraphStart(currentParagraph);
            int paragraphEnd = codeArea.paragraphEnd(currentParagraph);

            int caretPosition = codeArea.getCaretPosition();

            if (codeArea.getText(paragraphStart, caretPosition).trim().isEmpty())
            {
                modInf.update(paragraphStart - 1, paragraphEnd,
                        codeArea.getText().substring(caretPosition, paragraphEnd).trim(), 
                        paragraphStart - 1);
            }
        }
    }
}
