package com.lebastudios.sealcode.core.logic.completations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lebastudios.sealcode.core.controllers.MainStageController;
import com.lebastudios.sealcode.core.frontend.fxextends.Notification;
import com.lebastudios.sealcode.core.logic.config.FilePaths;
import com.lebastudios.sealcode.global.FileOperation;
import com.lebastudios.sealcode.global.MessageType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LangCompletationsJSON
{
    private String language = "default";
    private final List<KeyWordCompletation> keywordsCompletations = new ArrayList<>();
    private final List<LiveTemplateCompletation> liveTemplatesCompletations = new ArrayList<>();

    public List<KeyWordCompletation> getKeywordsCompletations()
    {
        return keywordsCompletations;
    }

    public List<LiveTemplateCompletation> getLiveTemplatesCompletations()
    {
        return liveTemplatesCompletations;
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
    
    public static LangCompletationsJSON createNewLangCompletations(String language)
    {
        LangCompletationsJSON langCompletationsJSON = new LangCompletationsJSON();
        langCompletationsJSON.language = language;
        langCompletationsJSON.saveToFile();
        return langCompletationsJSON;
    }
    
    public static LangCompletationsJSON readCompletationFromFile(String fileExtension)
    {
        File file = new File(FilePaths.getProgLangCompletationsDirectory() + fileExtension + ".json");

        if (!file.exists())
        {
            file = new File(FilePaths.getProgLangCompletationsDirectory() + "default.json");
        }

        try
        {
            LangCompletationsJSON langCompletationsJSON = new Gson().fromJson(FileOperation.readFile(file), LangCompletationsJSON.class);
            langCompletationsJSON.language = fileExtension;
            return langCompletationsJSON;
        } catch (Exception e)
        {
            MainStageController.getInstance().addNotification(new Notification(
                    "Error loading completations for " + fileExtension + " language", MessageType.Error)
            );
            e.printStackTrace();
            return null;
        }
    }

    public static class KeyWordCompletation extends Completation
    {
        public KeyWordCompletation()
        {
            super("keyword.png");
        }

        public KeyWordCompletation(String value)
        {
            super(value, "keyword.png");
        }

        @Override
        public String getDescription()
        {
            return "";
        }

        @Override
        public String getCompletation()
        {
            return getValue() + " ";
        }
    }

    public static class LiveTemplateCompletation extends Completation
    {
        private String description;
        private String completation;

        public LiveTemplateCompletation()
        {
            super("template.png");
        }

        public LiveTemplateCompletation(String value, String description, String completation)
        {
            super(value, "template.png");
            this.description = description;
            this.completation = completation;
        }

        public void setValue(String value)
        {
            this.value = value;
        }

        @Override
        public String getDescription()
        {
            return description;
        }

        public void setDescription(String description)
        {
            this.description = description;
        }

        @Override
        public String getCompletation()
        {
            return completation;
        }

        public void setCompletation(String completation)
        {
            this.completation = completation;
        }
    }
}
