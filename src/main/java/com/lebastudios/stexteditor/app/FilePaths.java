package com.lebastudios.stexteditor.app;

import com.lebastudios.stexteditor.app.config.Config;

public final class FilePaths
{
    public static String getConfigDirectory() { return "config/"; }
    public static String getSessionDirectory() { return "session/"; }
    
    public static String getProgLangSyntaxDirectory() { return "config/prog-lang/"; }
    
    public static String getImgDirectory() { return "/" + Config.getStaticInstance().editorConfig.theme + "/img/"; }
    public static String getStyleDirectory() { return "/" + Config.getStaticInstance().editorConfig.theme + "/style/"; }
}