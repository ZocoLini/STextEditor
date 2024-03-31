package com.lebastudios.stexteditor.app;

import com.lebastudios.stexteditor.app.config.Config;

public final class FilePaths
{
    public static String getConfigDirectory() { return "config/"; }
    public static String getSessionDirectory() { return "session/"; }
    
    public static String getProgLangSyntaxDirectory() { return "config/highlightingRules/"; }
    
    public static String getImgDirectory() { return "/" + Config.getStaticInstance().editorConfig.theme + "/img/"; }
    public static String getStyleDirectory() { return "/" + Config.getStaticInstance().editorConfig.theme + "/style/"; }
    
    public static String getDefaultImgDirectory() { return "/.Default/img/"; }
    public static String getDefaultStyleDirectory() { return "/.Default/style/"; }
}