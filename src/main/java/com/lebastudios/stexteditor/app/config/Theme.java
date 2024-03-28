package com.lebastudios.stexteditor.app.config;

import javafx.scene.paint.Color;

public class Theme extends JSONSaveable<Theme>
{
    public String themeName = "LightTheme";
    public Color colorPrimario = Color.RED;
    public Color colorSecundario = Color.BLUE;
    public Color colorTerciario = Color.GREEN;
    public Color colorTexto = Color.BLACK;
    public Color colorFondo = Color.WHITE;
    public Color colorSeleccion = Color.LIGHTBLUE;
    public Color colorError = Color.RED;
    public Color colorAdvertencia = Color.YELLOW;
    public Color colorInformacion = Color.BLUE;
    public Color colorKeywords = Color.DARKBLUE;
    public Color colorStrings = Color.DARKGREEN;
    public Color colorComments = Color.DARKGRAY;
    public Color colorNumbers = Color.DARKRED;
    public Color colorOperators = Color.DARKORANGE;
    public Color colorPunctuation = Color.DARKVIOLET;
    public Color colorFunctions = Color.DARKCYAN;
    public Color colorVariables = Color.DARKMAGENTA;
    public Color colorTypes = Color.DARKGOLDENROD;
    public Color colorConstants = Color.DARKKHAKI;

    private static Theme instance;

    public static Theme getStaticInstance()
    {
        if (instance == null)
        {
            instance = new Theme();
            instance.load();
        }

        return instance;
    }

    private Theme() {}
    
    @Override
    public String getFilePath()
    {
        return "config/themes/" + 
                Config.getStaticInstance().editorConfig.themeName + ".json";
    }

    @Override
    public JSONSaveable<Theme> getInstance()
    {
        return Theme.getStaticInstance();
    }

    @Override
    public void setInstance(Theme session)
    {
        instance = session;
    }

    @Override
    public Theme newInstance()
    {
        return new Theme();
    }

    @Override
    public void save()
    {
        System.err.println("You can't save a theme");
    }
}
