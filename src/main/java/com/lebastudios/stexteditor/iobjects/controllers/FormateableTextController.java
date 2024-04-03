package com.lebastudios.stexteditor.iobjects.controllers;

import com.lebastudios.stexteditor.applogic.Resources;
import com.lebastudios.stexteditor.iobjects.fxextends.FormateableTextTab;

public class FormateableTextController extends Controller
{
    private final FormateableTextTab tab;
    
    public FormateableTextController(FormateableTextTab tab)
    {
        super();
        this.tab = tab;

        instanciated = true;
    }

    @Override
    protected void onThemeChangue()
    {
        tab.setStyle(Resources.getThemeClassStyleFromFile("primary-bg"));
        
        // tab.getContent().setStyle(Resources.getThemeClassStyleFromFile("primary-bg"));
    }
}
