package com.lebastudios.stexteditor.app.config;

import javafx.scene.paint.Color;

public class Theme extends JSONSaveable<Theme>
{
    public String themeName = "LightTheme";
    public String colorPrimario = Color.RED.toString();
    public String colorSecundario = Color.BLUE.toString();
    public String colorTerciario = Color.GREEN.toString();
    public String colorTexto = Color.BLACK.toString();
    public String colorFondo = Color.WHITE.toString();
    public String colorSeleccion = Color.LIGHTBLUE.toString();
    public String colorError = Color.RED.toString();
    public String colorAdvertencia = Color.YELLOW.toString();
    public String colorInformacion = Color.BLUE.toString();
    public String colorKeywords = Color.DARKBLUE.toString();
    public String colorStrings = Color.DARKGREEN.toString();
    public String colorComments = Color.DARKGRAY.toString();
    public String colorNumbers = Color.DARKRED.toString();
    public String colorOperators = Color.DARKORANGE.toString();
    public String colorPunctuation = Color.DARKVIOLET.toString();
    public String colorFunctions = Color.DARKCYAN.toString();
    public String colorVariables = Color.DARKMAGENTA.toString();
    public String colorTypes = Color.DARKGOLDENROD.toString();
    public String colorConstants = Color.DARKKHAKI.toString();
    public String colorLine = Color.LIGHTGRAY.toString();

    private static Theme instance;

    public static Theme getStaticInstance()
    {
        if (instance == null)
        {
            instance = new Theme().load();
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
    public Theme newInstance()
    {
        return new Theme();
    }
}
