package com.lebastudios.sealcode.custom;

import com.github.javaparser.ParserConfiguration;
import com.lebastudios.sealcode.SealCodeApplication;
import com.lebastudios.sealcode.core.frontend.fxextends.IconTreeItem;
import com.lebastudios.sealcode.core.logic.Indexer;
import com.lebastudios.sealcode.core.logic.Starter;
import com.lebastudios.sealcode.core.logic.config.Session;
import com.lebastudios.sealcode.custom.logic.GlobalIndexer;
import com.lebastudios.sealcode.custom.logic.GlobalInspector;
import com.lebastudios.sealcode.custom.logic.database.MainDBManager;
import com.lebastudios.sealcode.custom.logic.database.MongoDBManager;
import com.lebastudios.sealcode.custom.logic.formatting.*;
import com.lebastudios.sealcode.custom.logic.java.JavaConfiguration;
import com.lebastudios.sealcode.custom.logic.java.completations.CompletationsFilter;
import com.lebastudios.sealcode.custom.logic.styling.BracketHighlighter;
import com.lebastudios.sealcode.core.logic.highlighting.KeyWordHighlighter;
import com.lebastudios.sealcode.events.AppEvents;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.IOException;

public class CustomStarter extends Starter
{
    private CustomStarter()
    {
    }

    public static void startCustomImplementation()
    {
        if (MainDBManager.getInstance().isAnyAccountConnected())
        {
            MongoDBManager.getInstance().pullUserFiles();
        }
        
        AppEvents.onAppExit.addListener(() -> new Thread(() -> {
                if (MainDBManager.getInstance().isAnyAccountConnected())
                {
                    MongoDBManager.getInstance().pushUserFiles();
                }
            }).start()
        );
        
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
        customiceSettings();
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

    private static void customiceSettings()
    {
        AppEvents.onLoadedSettingsTreeView.addListener(treeView ->
        {
            try
            {
                IconTreeItem<String> accountMenu = new FXMLLoader(SealCodeApplication.class.getResource(
                        "custom/settingsAdittion.fxml")
                ).load();
                
                treeView.getRoot().getChildren().add(accountMenu);
            } catch (IOException e)
            {
                System.err.println("Error while configuring custom settings tree view.");
            }
        });
    }
}
