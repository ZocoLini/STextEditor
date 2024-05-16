package com.lebastudios.sealcode.events;

import com.lebastudios.sealcode.core.frontend.fxextends.IconTreeItem;
import com.lebastudios.sealcode.core.frontend.fxextends.SealCodeArea;
import com.lebastudios.sealcode.core.frontend.fxextends.SettingsTreeView;
import com.lebastudios.sealcode.core.logic.Completation;
import com.lebastudios.sealcode.global.TextModInf;

import java.io.File;
import java.util.TreeSet;

public final class AppEvents
{
    /*  App Life Cicle  */
    public static final AppEvent onAppExit = new AppEvent();
    public static final AppEvent onAppStart = new AppEvent();
    
    /*  IDE Objects  */
        /*  Seal Code Área  */
            /*  Completations Popup  <completationsToShow, file, fileExtension, currentWord, allText> */
    public static final AppEvent5<TreeSet<Completation>, File, String, String, String> onCompletationsRequest = 
            new AppEvent5<>();
            /*  Modificaciones de Texto  <fileExtenssion (key), oldText, newTextInfo, textArea> */
    public static final AppMapEvent3<String, String, TextModInf, SealCodeArea> onTextModifiedBefore = new AppMapEvent3<>();
    public static final AppMapEvent3<String, String, TextModInf, SealCodeArea> onTextModifiedAfter = new AppMapEvent3<>();
    public static final AppMapEvent3<String, String, TextModInf, SealCodeArea> onTextDeleted = new AppMapEvent3<>();
    public static final AppMapEvent3<String, String, TextModInf, SealCodeArea> onTextInserted = new AppMapEvent3<>();
    public static final AppMapEvent3<String, String, TextModInf, SealCodeArea> onTextReplaced = new AppMapEvent3<>();
            /*Instanciación de un nuevo Seal Code Area*/
    public static final AppEvent1<SealCodeArea> onSealCodeAreaCreated = new AppEvent1<>();
    public static final AppEvent1<SealCodeArea> onSealCodeAreaDeleted = new AppEvent1<>();
    
    /*  Settings Pane   */
    public static final AppEvent onSettingsUpdate = new AppEvent();
    public static final AppEvent onThemeChange = new AppEvent();
    public static final AppEvent onPreferencesUpdate = new AppEvent();
    public static final AppEvent onProfileChange = new AppEvent();
    
    /* Customice interface */
        /* Settings TreeView */
    public static final AppEvent1<SettingsTreeView> onLoadedSettingsTreeView = new AppEvent1<>();
    public static final AppEvent1<IconTreeItem<String>> onLoadedSettingsIconTreeItem = new AppEvent1<>();
}