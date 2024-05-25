package com.lebastudios.sealcode.core.logic.completations;

import com.lebastudios.sealcode.core.logic.config.FilePaths;
import com.lebastudios.sealcode.core.logic.fileobj.JsonFile;
import com.lebastudios.sealcode.core.logic.fileobj.LangCompletationsJSON;
import com.lebastudios.sealcode.events.AppEvents;

import java.io.File;
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
        JsonFile<LangCompletationsJSON> langCompletations = new JsonFile<>(
                new File(FilePaths.getCompletationsDir() + fileExtension + ".json"),
                new LangCompletationsJSON());

        TreeSet<Completation> completationsSet = new TreeSet<>();

        completationsSet.addAll(langCompletations.getInstance().keywordsCompletations);
        completationsSet.addAll(langCompletations.getInstance().liveTemplatesCompletations);

        return completationsSet;
    }
}
