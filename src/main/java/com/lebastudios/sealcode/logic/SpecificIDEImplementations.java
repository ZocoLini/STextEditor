package com.lebastudios.sealcode.logic;

import com.lebastudios.sealcode.logic.formatting.*;
import com.lebastudios.sealcode.logic.txtformatter.BracketHighlighter;
import com.lebastudios.sealcode.events.AppEvents;
import com.lebastudios.sealcode.logic.txtformatter.KeyWordHighlighter;

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
        AppEvents.onSealCodeAreaCreated.addListener(KeyWordHighlighter::new);
    }
    
    private static void setOnTextModificationEvents()
    {
        AppEvents.onTextModifiedAfter.addListener(new TabRemover());
        AppEvents.onTextModifiedBefore.addListener(new Indent());
        AppEvents.onTextInserted.addListener(new ParenPairInsert());
        AppEvents.onTextDeleted.addListener(new JumpBlankLines());
        AppEvents.onTextDeleted.addListener(new ParenPairRemove());
    }
}
