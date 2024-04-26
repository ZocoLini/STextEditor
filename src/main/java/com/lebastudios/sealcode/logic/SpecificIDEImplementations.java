package com.lebastudios.sealcode.logic;

import com.lebastudios.sealcode.events.AppEvents;
import com.lebastudios.sealcode.logic.completations.MethodCompletationsFilter;
import com.lebastudios.sealcode.logic.formatting.*;
import com.lebastudios.sealcode.logic.java.indexer.Indexer;
import com.lebastudios.sealcode.logic.styling.BracketHighlighter;
import com.lebastudios.sealcode.logic.styling.KeyWordHighlighter;

public class SpecificIDEImplementations
{
    private SpecificIDEImplementations() {}
    
    public static void implementation()
    {
        Thread thread = new Thread(Indexer::index);
        thread.start();

        try
        {
            thread.join();
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }

        setOnTextModificationEvents();
        setOnSealCodeAreaCreatedEvents();
        setOnCompletationsRequestedEvents();
    }
    
    private static void setOnCompletationsRequestedEvents()
    {
        AppEvents.onCompletationsRequest.addListener(new MethodCompletationsFilter());
    }
    
    private static void setOnSealCodeAreaCreatedEvents()
    {
        AppEvents.onSealCodeAreaCreated.addListener(BracketHighlighter::new);
        AppEvents.onSealCodeAreaCreated.addListener(KeyWordHighlighter::new);
    }
    
    private static void setOnTextModificationEvents()
    {
        AppEvents.onTextModifiedAfter.addListener("All", new TabRemover());
        AppEvents.onTextModifiedAfter.addListener("java", new FormatTextInsert());
        AppEvents.onTextModifiedBefore.addListener("java", new IndentNextLineInsert());
        AppEvents.onTextInserted.addListener("All", new ParenPairInsert());
        AppEvents.onTextDeleted.addListener("java", new JumpBlankLines());
        AppEvents.onTextDeleted.addListener("java", new ParenPairRemove());
    }
}
