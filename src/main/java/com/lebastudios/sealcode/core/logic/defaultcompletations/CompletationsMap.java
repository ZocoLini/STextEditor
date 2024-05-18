package com.lebastudios.sealcode.core.logic.defaultcompletations;

import com.google.gson.Gson;
import com.lebastudios.sealcode.core.controllers.MainStageController;
import com.lebastudios.sealcode.core.frontend.fxextends.Notification;
import com.lebastudios.sealcode.core.logic.config.FilePaths;
import com.lebastudios.sealcode.events.AppEvents;
import com.lebastudios.sealcode.core.logic.Completation;
import com.lebastudios.sealcode.global.FileOperation;
import com.lebastudios.sealcode.global.MessageType;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class CompletationsMap
{
    private static CompletationsMap instance;

    public static CompletationsMap getInstance()
    {
        if (instance == null) instance = new CompletationsMap();

        return instance;
    }
    
    private final Map<String, TreeSet<Completation>> loadedCompletations = new HashMap<>();

    private CompletationsMap() 
    {
        AppEvents.onGlobalConfigUpdate.addListener(() -> 
        {
            final Map<String, TreeSet<Completation>> auxMap = new HashMap<>(loadedCompletations);
            for (var variable : auxMap.keySet())
            {
                loadedCompletations.put(variable, loadCompletations(variable));
            }
        });
    }
    
    public TreeSet<Completation> getCompletations(String fileExtesion)
    {
        return loadedCompletations.computeIfAbsent(fileExtesion, this::loadCompletations);
    }

    private TreeSet<Completation> loadCompletations(String fileExtension)
    {
        Completations completations = readCompletationFromFile(fileExtension);

        TreeSet<Completation> completationsSet = new TreeSet<>();

        completationsSet.addAll(Arrays.asList(completations.keywordsCompletations));
        completationsSet.addAll(Arrays.asList(completations.liveTemplatesCompletations));

        return completationsSet;
    }

    private static Completations readCompletationFromFile(String fileExtension)
    {
        File file = new File(FilePaths.getProgLangCompletationsDirectory() + fileExtension + ".json");

        if (!file.exists())
        {
            file = new File(FilePaths.getProgLangCompletationsDirectory() + "default.json");
        }

        try
        {
            return new Gson().fromJson(FileOperation.readFile(file), Completations.class);
        }
        catch (Exception e)
        {
            MainStageController.getInstance().addNotification(new Notification(
                    "Error loading completations for " + fileExtension + " language", MessageType.Error)
            );
            e.printStackTrace();
        }

        return new Completations();
    }
    
    private static class Completations
    {
        KeyWordCompletation[] keywordsCompletations = new KeyWordCompletation[0];
        LiveTemplateCompletation[] liveTemplatesCompletations = new LiveTemplateCompletation[0];
    }
}
