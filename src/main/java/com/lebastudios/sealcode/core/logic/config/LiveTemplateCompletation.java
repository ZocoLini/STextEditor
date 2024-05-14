package com.lebastudios.sealcode.core.logic.config;

import com.lebastudios.sealcode.core.logic.Completation;

class LiveTemplateCompletation extends Completation
{
    private String description;
    private String completation;
    
    public LiveTemplateCompletation()
    {
        super("template.png");
    }

    @Override
    public String getDescription()
    {
        return description;
    }

    @Override
    public String getCompletation()
    {
        return completation;
    }
}
