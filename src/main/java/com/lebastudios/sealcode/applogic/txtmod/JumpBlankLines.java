package com.lebastudios.sealcode.applogic.txtmod;

import com.lebastudios.sealcode.frontend.fxextends.SealCodeArea;

public class JumpBlankLines implements ITextMod
{
    @Override
    public TextModInf onTextDeleted(String oldText, TextModInf modInf, SealCodeArea codeArea)
    {
        // Cuando se borra y el paragrafo solo son espacios, se borra_todo el paragrafo
        final var currentParagraph = codeArea.getCurrentParagraph();
        
        if (modInf.textModificated.isEmpty() && oldText.equals(" ") && currentParagraph != 0)
        {
            int paragraphStart = codeArea.paragraphStart(currentParagraph);
            int paragraphEnd = codeArea.paragraphEnd(currentParagraph);

            int caretPosition = codeArea.getCaretPosition();

            if (codeArea.getText(paragraphStart, caretPosition).trim().isEmpty())
            {
                return new TextModInf(paragraphStart - 1, paragraphEnd,
                        codeArea.getText().substring(caretPosition, paragraphEnd).trim(), 
                        paragraphStart - 1);
            }
        }
        
        return modInf;
    }
}
