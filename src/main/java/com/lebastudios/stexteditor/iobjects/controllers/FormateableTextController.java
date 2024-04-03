package com.lebastudios.stexteditor.iobjects.controllers;

import com.lebastudios.stexteditor.applogic.FilePaths;
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
        System.out.println(Resources.getClassStyleFromFile(FilePaths.getStyleDirectory() + "theme.css",
                "hola"));
        
        // estilo del tab: tab.setStyle("-fx-background-color: red");
        
        // estilo del contenido: tab.getContent().setStyle("-fx-background-color: red");
    }
}
