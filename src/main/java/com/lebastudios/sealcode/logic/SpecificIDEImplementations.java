package com.lebastudios.sealcode.logic;

import com.github.javaparser.ParserConfiguration;
import com.lebastudios.sealcode.config.Session;
import com.lebastudios.sealcode.events.AppEvents;
import com.lebastudios.sealcode.logic.formatting.*;
import com.lebastudios.sealcode.logic.java.JavaConfiguration;
import com.lebastudios.sealcode.logic.java.completations.CompletationsFilter;
import com.lebastudios.sealcode.logic.java.indexer.GlobalIndexer;
import com.lebastudios.sealcode.logic.java.inspections.GlobalInspector;
import com.lebastudios.sealcode.logic.styling.BracketHighlighter;
import com.lebastudios.sealcode.logic.styling.KeyWordHighlighter;
import com.lebastudios.sealcode.util.Indexer;

import java.io.File;

public class SpecificIDEImplementations
{
    private SpecificIDEImplementations() {}
    
    public static void implementation()
    {
        JavaConfiguration.getInstance().setLangLvl(ParserConfiguration.LanguageLevel.JAVA_18);

        GlobalInspector.startInspector();
        GlobalIndexer.startIndexer();
        
        Thread thread = new Thread(() -> Indexer.getIndexer()
                .index(new File(Session.getStaticInstance().proyectDirectory + "/src")));
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
        AppEvents.onCompletationsRequest.addListener(new CompletationsFilter());
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
