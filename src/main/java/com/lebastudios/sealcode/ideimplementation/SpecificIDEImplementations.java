package com.lebastudios.sealcode.ideimplementation;

import com.lebastudios.sealcode.ideimplementation.txtformatter.BracketHighlighter;
import com.lebastudios.sealcode.ideimplementation.formatting.Indent;
import com.lebastudios.sealcode.ideimplementation.formatting.JumpBlankLines;
import com.lebastudios.sealcode.ideimplementation.formatting.ParenPairInsert;
import com.lebastudios.sealcode.ideimplementation.formatting.ParenPairRemove;
import com.lebastudios.sealcode.events.AppEvents;

public class SpecificIDEImplementations
{
    private SpecificIDEImplementations() {}
    
    public static void implementation()
    {
        setOnTextModificationEvents();
        setOnSealCodeAreaCreatedEvents();
    }
    
    private static void setOnSealCodeAreaCreatedEvents()
    {
        AppEvents.onSealCodeAreaCreated.addListener(BracketHighlighter::new);
    }
    
    private static void setOnTextModificationEvents()
    {
        AppEvents.onTextInserted.addListener(new ParenPairInsert());
        AppEvents.onTextModified.addListener(new Indent());
        AppEvents.onTextDeleted.addListener(new JumpBlankLines());
        AppEvents.onTextDeleted.addListener(new ParenPairRemove());
    }
}
