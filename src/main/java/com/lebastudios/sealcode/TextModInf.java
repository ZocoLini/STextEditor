package com.lebastudios.sealcode;

public class TextModInf
{ 
    public int start;
    public int end;
    public String textModificated;
    public int caretPos = -1;
    
    public TextModInf(int start, int end, String textModificated, int caretPos)
    {
        update(start, end, textModificated, caretPos);
    }
    
    public TextModInf(int start, int end, String textModificated)
    {
        this(start, end, textModificated, -1);
    }
    
    public TextModInf update(int start, int end, String textModificated, int caretPos)
    {
        if (caretPos == -1 && this.caretPos != -1) 
        {
           throw new IllegalArgumentException("Caret position can't be -1");
        }
        
        this.start = start;
        this.end = end;
        this.textModificated = textModificated;
        this.caretPos = caretPos;
        
        return this;
    }

    public TextModInf update(int start, int end, String textModificated)
    {
        return update(start, end, textModificated, this.caretPos);
    }

    public TextModInf update(String newText)
    {
        return update(start, end, newText); 
    }
}
