package com.lebastudios.stexteditor.iobjects.controllers;

import com.lebastudios.stexteditor.iobjects.fxextends.FormateableTextTab;

public class FormateableTextController extends Controller
{
    private final FormateableTextTab tab;
    
    public FormateableTextController(FormateableTextTab tab)
    {
        super();
        this.tab = tab;
    }

    @Override
    protected void onThemeChangue()
    {
        // estilo del tab: tab.setStyle("-fx-background-color: red");
        
        // estilo del contenido: tab.getContent().setStyle("-fx-background-color: red");
    }
}
