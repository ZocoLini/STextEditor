package com.lebastudios.sealcode.fileobj;

import com.lebastudios.sealcode.completations.Completation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LangCompletationsJSON
{
    public final List<KeyWordCompletation> keywordsCompletations = new ArrayList<>();
    public final List<LiveTemplateCompletation> liveTemplatesCompletations = new ArrayList<>();

    public Collection<? extends Completation> getCompletations()
    {
        List<Completation> completations = new ArrayList<>();
        completations.addAll(keywordsCompletations);
        completations.addAll(liveTemplatesCompletations);
        return completations;
    }

    public LiveTemplateCompletation getLiveTemplateCompletation(String value)
    {
        for (var completation : liveTemplatesCompletations)
        {
            if (completation.getValue().equals(value))
            {
                return completation;
            }
        }
        return null;
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
