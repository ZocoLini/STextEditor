package com.lebastudios.sealcode.events;

import com.lebastudios.sealcode.frontend.IconTreeItem;
import com.lebastudios.sealcode.frontend.SealCodeArea;
import com.lebastudios.sealcode.frontend.SettingsTreeView;
import com.lebastudios.sealcode.completations.Completation;
import com.lebastudios.sealcode.fileobj.FileObj;
import com.lebastudios.sealcode.TextModInf;

import java.io.File;
import java.util.TreeSet;

public final class AppEvents
{
    /*  App Life Cicle  */
    public static final Event onAppExit = new Event();
    public static final Event onAppStart = new Event();
    
    /* FileObj */
        /* When a fileobj is saved <filePathSaved> */
    public static final Event2<String, FileObj> onFileObjModified = new Event2<>();
    
    /*  IDE Interface Events Objects  */
        /*  Seal Code Área  */
            /*  Completations Popup  <completationsToShow, file, fileExtension, currentWord, allText> */
    public static final Event5<TreeSet<Completation>, File, String, String, String> onCompletationsRequest = 
            new Event5<>();
            /*  Modificaciones de Texto  <fileExtenssion (key), oldText, newTextInfo, textArea> */
    public static final MappedEvent3<String, String, TextModInf, SealCodeArea> onTextModifiedBefore = new MappedEvent3<>();
    public static final MappedEvent3<String, String, TextModInf, SealCodeArea> onTextModifiedAfter = new MappedEvent3<>();
    public static final MappedEvent3<String, String, TextModInf, SealCodeArea> onTextDeleted = new MappedEvent3<>();
    public static final MappedEvent3<String, String, TextModInf, SealCodeArea> onTextInserted = new MappedEvent3<>();
    public static final MappedEvent3<String, String, TextModInf, SealCodeArea> onTextReplaced = new MappedEvent3<>();
            /*Instanciación de un nuevo Seal Code Area*/
    public static final Event1<SealCodeArea> onSealCodeAreaCreated = new Event1<>();
    public static final Event1<SealCodeArea> onSealCodeAreaDeleted = new Event1<>();
    
    /*  Settings Pane   */
        /* Global Config */
    public static final Event onGlobalConfigUpdate = new Event();
    
    /* Customice interface */
        /* Settings TreeView */
    public static final Event1<SettingsTreeView> onLoadedSettingsTreeView = new Event1<>();
    public static final Event1<IconTreeItem<String>> onLoadedSettingsIconTreeItem = new Event1<>();
}