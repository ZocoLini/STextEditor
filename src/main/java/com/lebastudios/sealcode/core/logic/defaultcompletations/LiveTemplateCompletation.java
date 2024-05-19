package com.lebastudios.sealcode.core.logic.defaultcompletations;

import com.lebastudios.sealcode.core.logic.Completation;

public class LiveTemplateCompletation extends Completation
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
