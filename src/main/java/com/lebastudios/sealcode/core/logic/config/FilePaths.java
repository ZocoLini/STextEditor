package com.lebastudios.sealcode.core.logic.config;

import java.nio.file.Paths;

@SuppressWarnings("SameReturnValue")
public final class FilePaths
{
    public static String getUserDirectory() {return Paths.get(System.getProperty("user.home"), ".seal").toString();}
    
    public static String getGlobalConfigDirectory() {return getUserDirectory() + "/config/";}
    
    public static String getProyectConfigDirectory() {return getActualProyectDirectory() + "/.seal/";}

    public static String getActualProyectDirectory() {return Session.getStaticInstance().proyectDirectory + "/";}

    public static String getAppDirectory() {return System.getProperty("user.dir");}

    public static String getSessionDirectory() {return getAppDirectory() + "/session/";}
    
    public static String getHighlightingRulesDir() {return getAppDirectory() + "/highlightingRules/";}

    public static String getCompletationsDir() {return getAppDirectory() + "/completations/" ;}

    public static String getThemeDirectory() {return getAppDirectory() + "/themes/";}
    
    public static String getIconDirectory()
    {
        return getThemeDirectory() + GlobalConfig.getStaticInstance().editorConfig.theme + "/icon/";
    }

    public static String getStyleDirectory()
    {
        return getThemeDirectory() + GlobalConfig.getStaticInstance().editorConfig.theme + "/style/";
    }
    
    public static String getLangStyleFile() {return getStyleDirectory() + "default.css";}

    public static String getDefaultThemeDirectory() {return getThemeDirectory() + "Light/";}
    
    public static String getDefaultIconDirectory() {return getDefaultThemeDirectory() + "icon/";}

    public static String getDefaultStyleDirectory() {return getDefaultThemeDirectory() + "style/";}

    public static String getDefaultLangStyleFile() {return getDefaultStyleDirectory() + "default.css";}
    
    public static String getDefaultIconFile() {return getDefaultIconDirectory() + "unknown.png";}

    public static String getDefaulThemeFile() {return getDefaultStyleDirectory() + "theme.css";}

    public static String getEquivalentExtensionsFile()
    {
        return getHighlightingRulesDir() + "equivalentExtensions.json";
    }
}