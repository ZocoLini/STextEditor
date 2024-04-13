package com.lebastudios.stexteditor.applogic;

import com.lebastudios.stexteditor.applogic.config.global.GlobalConfig;
import com.lebastudios.stexteditor.applogic.config.global.Session;

@SuppressWarnings("SameReturnValue")
public final class FilePaths
{
    public static String getActualProyectDirectory() { return Session.getStaticInstance().proyectDirectory + "/"; }
    
    public static String getGlobalConfigDirectory() { return "config/"; }
    public static String getProyectConfigDirectory() { return getActualProyectDirectory() + "/.seal/"; }
    public static String getSessionDirectory() { return "session/"; }
    
    public static String getProgLangSyntaxDirectory() { return getGlobalConfigDirectory() + "highlightingRules/"; }
    
    public static String getImgDirectory() { return "/" + GlobalConfig.getStaticInstance().editorConfig.theme + "/img/"; }
    public static String getIconDirectory() { return "/" + GlobalConfig.getStaticInstance().editorConfig.theme + "/icon/"; }
    public static String getStyleDirectory() { return "/" + GlobalConfig.getStaticInstance().editorConfig.theme + "/style/"; }
    
    public static String getLangStyleFile() { return getStyleDirectory() + "default.css"; }
    
    public static String getDefaultDirectory() { return "/Light/"; }
    
    public static String getDefaultImgDirectory() { return getDefaultDirectory() + "img/"; }
    public static String getDefaultIconDirectory() { return getDefaultDirectory() + "icon/"; }
    public static String getDefaultStyleDirectory() { return getDefaultDirectory() + "style/"; }
    
    public static String getDefaultLangStyleFile() { return getDefaultStyleDirectory() + "default.css"; }
    public static String getDefaultImgFile() { return getDefaultImgDirectory() + "notfoundtype.png"; }
    public static String getDefaultIconFile() { return getDefaultImgDirectory() + "notfoundtype.png"; }
    public static String getDefaulThemeFile() { return getDefaultStyleDirectory() + "theme.css"; }
}