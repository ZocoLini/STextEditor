package com.lebastudios.sealcode.core.logic.defaultcompletations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.j2objc.annotations.Property;
import com.lebastudios.sealcode.core.controllers.MainStageController;
import com.lebastudios.sealcode.core.frontend.fxextends.Notification;
import com.lebastudios.sealcode.core.logic.config.FilePaths;
import com.lebastudios.sealcode.global.FileOperation;
import com.lebastudios.sealcode.global.MessageType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LangCompletations
{
    private String language = "default";
    private List<KeyWordCompletation> keywordsCompletations = new ArrayList<>();
    private List<LiveTemplateCompletation> liveTemplatesCompletations = new ArrayList<>();

    public List<KeyWordCompletation> getKeywordsCompletations()
    {
        return keywordsCompletations;
    }

    public void setKeywordsCompletations(List<KeyWordCompletation> keywordsCompletations)
    {
        this.keywordsCompletations = keywordsCompletations;
    }

    public List<LiveTemplateCompletation> getLiveTemplatesCompletations()
    {
        return liveTemplatesCompletations;
    }

    public void setLiveTemplatesCompletations(List<LiveTemplateCompletation> liveTemplatesCompletations)
    {
        this.liveTemplatesCompletations = liveTemplatesCompletations;
    }

    public void saveToFile()
    {
        File file = new File(FilePaths.getProgLangCompletationsDirectory() + language + ".json");

        try
        {
            FileOperation.writeFile(file, new GsonBuilder().setPrettyPrinting().create().toJson(this));
            CompletationsMap.getInstance().clearCompletations();
        } catch (Exception e)
        {
            MainStageController.getInstance().addNotification(new Notification(
                    "Error saving completations for " + language + " language",
                    MessageType.Error)
            );
        }
    }
    
    public static LangCompletations createNewLangCompletations(String language)
    {
        LangCompletations langCompletations = new LangCompletations();
        langCompletations.language = language;
        langCompletations.saveToFile();
        return langCompletations;
    }
    
    public static LangCompletations readCompletationFromFile(String fileExtension)
    {
        File file = new File(FilePaths.getProgLangCompletationsDirectory() + fileExtension + ".json");

        if (!file.exists())
        {
            file = new File(FilePaths.getProgLangCompletationsDirectory() + "default.json");
        }

        try
        {
            LangCompletations langCompletations = new Gson().fromJson(FileOperation.readFile(file), LangCompletations.class);
            langCompletations.language = fileExtension;
            return langCompletations;
        } catch (Exception e)
        {
            MainStageController.getInstance().addNotification(new Notification(
                    "Error loading completations for " + fileExtension + " language", MessageType.Error)
            );
            e.printStackTrace();
            return null;
        }
    }
}
