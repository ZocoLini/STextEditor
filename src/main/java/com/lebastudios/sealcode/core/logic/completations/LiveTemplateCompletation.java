package com.lebastudios.sealcode.core.logic.completations;

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
