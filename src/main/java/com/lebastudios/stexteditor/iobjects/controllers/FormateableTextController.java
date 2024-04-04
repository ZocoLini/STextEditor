package com.lebastudios.stexteditor.iobjects.controllers;

import com.lebastudios.stexteditor.applogic.config.Theme;
import com.lebastudios.stexteditor.iobjects.fxextends.FormateableTextTab;

public class FormateableTextController extends Controller<FormateableTextTab>
{
    public FormateableTextController(FormateableTextTab tab)
    {
        super(tab);

        instanciated = true;
    }

    @Override
    protected void onThemeChangue()
    {
        representingObject.setStyle(Theme.getInstance().getPrimaryBgStyle());
        
        // tab.getContent().setStyle(Resources.getThemeClassStyleFromFile("primary-bg"));
    }
}
