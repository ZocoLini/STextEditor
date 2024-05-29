package com.lebastudios.sealcodeplugins;

import com.github.javaparser.ParserConfiguration;
import com.lebastudios.sealcode.IPlugin;
import com.lebastudios.sealcode.events.AppEvents;
import com.lebastudios.sealcode.Indexer;
import com.lebastudios.sealcode.config.Session;
import com.lebastudios.sealcodeplugins.formatting.*;
import com.lebastudios.sealcodeplugins.completations.CompletationsFilter;
import com.lebastudios.sealcodeplugins.styling.BracketHighlighter;

import java.io.File;

public class JavaLangImplPlugin implements IPlugin
{
    @Override
    public void initialize()
    {
        System.out.println("Inicializando Java Lang Impl Plugin");

        JavaConfiguration.getInstance().setLangLvl(ParserConfiguration.LanguageLevel.JAVA_18);

        GlobalInspector.startInspector();
        GlobalIndexer.startIndexer();

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