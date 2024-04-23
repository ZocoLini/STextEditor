package com.lebastudios.sealcode.applogic.txtmod;

public class TextModInf
{ 
    public int start;
    public int end;
    public String textModificated;
    public int caretPos;
    
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
        this.start = start;
        this.end = end;
        this.textModificated = textModificated;
        this.caretPos = caretPos;
        
        return this;
    }

    public TextModInf update(int start, int end, String textModificated)
    {
        return update(start, end, textModificated, -1);
    }
}
