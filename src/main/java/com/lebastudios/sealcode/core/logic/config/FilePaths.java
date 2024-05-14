package com.lebastudios.sealcode.core.logic.config;

import java.nio.file.Paths;

@SuppressWarnings("SameReturnValue")
public final class FilePaths
{
    public static String getGlobalConfigDirectory() {return getUserDirectory() + "/config/";}

    public static String getUserDirectory() {return Paths.get(System.getProperty("user.home"), ".seal").toString();}

    public static String getProyectConfigDirectory() {return getActualProyectDirectory() + "/.seal/";}

    public static String getActualProyectDirectory() {return Session.getStaticInstance().proyectDirectory + "/";}

    public static String getAppDirectory() {return System.getProperty("user.dir");}

    public static String getSessionDirectory() {return getAppDirectory() + "/session/";}
    
    public static String getProgLangSyntaxDirectory() {return getAppDirectory() + "/highlightingRules/";}

    public static String getProgLangCompletationsDirectory() {return getAppDirectory() + "/completations/" 
            + GlobalConfig.getStaticInstance().userPrefs.profile + "/";}

    public static String getIconDirectory()
    {
        return "/" + GlobalConfig.getStaticInstance().editorConfig.theme + "/icon/";
    }

    public static String getLangStyleFile() {return getStyleDirectory() + "default.css";}

    public static String getStyleDirectory()
    {
        return "/" + GlobalConfig.getStaticInstance().editorConfig.theme + "/style/";
    }

    public static String getDefaultIconDirectory() {return getDefaultDirectory() + "icon/";}

    public static String getDefaultDirectory() {return "/Light/";}

    public static String getDefaultLangStyleFile() {return getDefaultStyleDirectory() + "default.css";}

    public static String getDefaultStyleDirectory() {return getDefaultDirectory() + "style/";}

    public static String getDefaultIconFile() {return getDefaultIconDirectory() + "fileunknown.png";}

    public static String getDefaulThemeFile() {return getDefaultStyleDirectory() + "theme.css";}
}