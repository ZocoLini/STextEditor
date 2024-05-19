package com.lebastudios.sealcode.core.logic.defaultcompletations;

import com.lebastudios.sealcode.events.AppEvents;
import com.lebastudios.sealcode.core.logic.Completation;

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
    
    public void clearCompletations()
    {
        loadedCompletations.clear();
    }
    
    public TreeSet<Completation> getCompletations(String fileExtesion)
    {
        return loadedCompletations.computeIfAbsent(fileExtesion, this::loadCompletations);
    }

    private TreeSet<Completation> loadCompletations(String fileExtension)
    {
        LangCompletations langCompletations = LangCompletations.readCompletationFromFile(fileExtension);

        TreeSet<Completation> completationsSet = new TreeSet<>();

        completationsSet.addAll(langCompletations.getKeywordsCompletations());
        completationsSet.addAll(langCompletations.getLiveTemplatesCompletations());

        return completationsSet;
    }
}
