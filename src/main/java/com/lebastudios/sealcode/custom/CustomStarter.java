package com.lebastudios.sealcode.custom;

import com.github.javaparser.ParserConfiguration;
import com.lebastudios.sealcode.core.logic.Indexer;
import com.lebastudios.sealcode.core.logic.Starter;
import com.lebastudios.sealcode.core.logic.config.Session;
import com.lebastudios.sealcode.custom.logic.GlobalIndexer;
import com.lebastudios.sealcode.custom.logic.GlobalInspector;
import com.lebastudios.sealcode.custom.logic.database.MainDBConnection;
import com.lebastudios.sealcode.custom.logic.formatting.*;
import com.lebastudios.sealcode.custom.logic.java.JavaConfiguration;
import com.lebastudios.sealcode.custom.logic.java.completations.CompletationsFilter;
import com.lebastudios.sealcode.custom.logic.styling.BracketHighlighter;
import com.lebastudios.sealcode.custom.logic.styling.KeyWordHighlighter;
import com.lebastudios.sealcode.events.AppEvents;

import java.io.File;

public class CustomStarter extends Starter
{
    private CustomStarter()
    {
    }

    public static void startCustomImplementation()
    {
        JavaConfiguration.getInstance().setLangLvl(ParserConfiguration.LanguageLevel.JAVA_18);

        GlobalInspector.startInspector();
        GlobalIndexer.startIndexer();
        MainDBConnection.startDatabase();

        File filesrc = new File(Session.getStaticInstance().proyectDirectory + "/src");
        if (filesrc.exists()) 
        {
            Indexer.getIndexer().index(new File(Session.getStaticInstance().proyectDirectory + "/src"));
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
