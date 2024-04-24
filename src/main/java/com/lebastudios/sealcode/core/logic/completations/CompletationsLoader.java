package com.lebastudios.sealcode.core.logic.completations;

import com.google.gson.Gson;
import com.lebastudios.sealcode.util.FileOperation;
import com.lebastudios.sealcode.config.FilePaths;
import com.lebastudios.sealcode.config.GlobalConfig;
import com.lebastudios.sealcode.controllers.MainStageController;
import com.lebastudios.sealcode.util.MessageType;
import com.lebastudios.sealcode.core.frontend.fxextends.Notification;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class CompletationsLoader
{
    static Map<String, Completations> loadedCompletations = new HashMap<>();
    
    public static TreeSet<Completation> getAllCompletations(String fileExtension)
    {
        Completations completations = getCompletations(fileExtension);

        TreeSet<Completation> completationsSet = new TreeSet<>();
        
        completationsSet.addAll(Arrays.asList(completations.keywordsCompletations));
        completationsSet.addAll(Arrays.asList(completations.liveTemplatesCompletations));
        
        return completationsSet;
    }    
    
    private static Completations getCompletations(String fileExtension)
    {
        return loadedCompletations.computeIfAbsent(
                GlobalConfig.getStaticInstance().userPrefs.profile + fileExtension, 
                k -> loadCompletations(fileExtension));
    }
    
    private static Completations loadCompletations(String fileExtension)
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
            MainStageController.getInstance().notificationsContainer.addNotification(new Notification(
                    "Error loading completations for " + fileExtension + " language", MessageType.Error)
            );
            e.printStackTrace();
        }
        
        return new Completations();
    }
    
    public static class Completations
    {
        public KeyWordCompletation[] keywordsCompletations = new KeyWordCompletation[0];
        public LiveTemplateCompletation[] liveTemplatesCompletations = new LiveTemplateCompletation[0];
    }
}