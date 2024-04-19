package com.lebastudios.sealcode.applogic;

import com.lebastudios.sealcode.applogic.config.GlobalConfig;
import com.lebastudios.sealcode.applogic.config.Session;

import java.nio.file.Paths;

@SuppressWarnings("SameReturnValue")
public final class FilePaths
{
    public static String getGlobalConfigDirectory() {return getUserDirectory() + "/config/";}

    public static String getUserDirectory() {return Paths.get(System.getProperty("user.home"), ".seal").toString();}

    public static String getSessionDirectory() {return getUserDirectory() + "/session/";}

    public static String getProyectConfigDirectory() {return getActualProyectDirectory() + "/.seal/";}

    public static String getActualProyectDirectory() {return Session.getStaticInstance().proyectDirectory + "/";}

    public static String getProgLangSyntaxDirectory() {return getAppDirectory() + "/highlightingRules/";}

    public static String getAppDirectory() {return System.getProperty("user.dir");}

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