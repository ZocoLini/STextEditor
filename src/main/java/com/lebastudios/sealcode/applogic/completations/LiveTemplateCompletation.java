package com.lebastudios.sealcode.applogic.completations;

public class LiveTemplateCompletation extends Completation
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
