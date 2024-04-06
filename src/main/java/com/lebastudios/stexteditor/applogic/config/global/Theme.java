package com.lebastudios.stexteditor.applogic.config.global;

import com.lebastudios.stexteditor.TextEditorApplication;
import com.lebastudios.stexteditor.applogic.Resources;
import com.lebastudios.stexteditor.events.GlobalEvents;

import java.io.File;

public class Theme
{
    private static Theme instance;
    
    public static Theme getInstance()
    {
        if (instance == null) 
        {
            instance = new Theme();
        }
        
        return instance;
    }
    
    private String primaryBgStyle;
    private String secondaryBgStyle;
    private String thirdBgStyle;
    private String fourthBgStyle;
    
    private String errorTextStyle;
    private String succesTextStyle;
    private String warningTextStyle;
    private String infoTextStyle;
    private String normalTextStyle;
    
    private Theme()
    {
        loadThemeStyles();
    }
    
    private void loadThemeStyles()
    {
        primaryBgStyle = Resources.getThemeClassStyleFromFile("primary-bg");
        secondaryBgStyle = Resources.getThemeClassStyleFromFile("secondary-bg");
        thirdBgStyle = Resources.getThemeClassStyleFromFile("third-bg");
        fourthBgStyle = Resources.getThemeClassStyleFromFile("fourth-bg");
        
        errorTextStyle = Resources.getThemeClassStyleFromFile("error-text");
        succesTextStyle = Resources.getThemeClassStyleFromFile("succes-text");
        warningTextStyle = Resources.getThemeClassStyleFromFile("warning-text-bg");
        infoTextStyle = Resources.getThemeClassStyleFromFile("info-text");
        normalTextStyle = Resources.getThemeClassStyleFromFile("normal-text");
    }

    public String getPrimaryBgStyle()
    {
        return primaryBgStyle;
    }

    public String getSecondaryBgStyle()
    {
        return secondaryBgStyle;
    }

    public String getThirdBgStyle()
    {
        return thirdBgStyle;
    }

    public String getFourthBgStyle()
    {
        return fourthBgStyle;
    }

    public String getErrorTextStyle()
    {
        return errorTextStyle;
    }

    public String getSuccesTextStyle()
    {
        return succesTextStyle;
    }

    public String getWarningTextStyle()
    {
        return warningTextStyle;
    }

    public String getInfoTextStyle()
    {
        return infoTextStyle;
    }

    public String getNormalTextStyle()
    {
        return normalTextStyle;
    }

    public boolean setTheme(String themeName)
    {
        File themeFile = new File(TextEditorApplication.class.getResource(themeName).getPath());
        if (!themeFile.exists() || !themeFile.isDirectory()) 
        {
            return false;
        }
        
        GlobalConfig.getStaticInstance().editorConfig.theme = themeName;
        
        loadThemeStyles();

        GlobalEvents.onThemeChanged.invoke();
        
        return true;
    }
}
