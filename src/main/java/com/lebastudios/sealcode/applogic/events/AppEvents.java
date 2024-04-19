package com.lebastudios.sealcode.applogic.events;

public final class AppEvents
{
    public static final OnAppExit OnAppExit = new OnAppExit();
    public static class OnAppExit extends AppEvent {}
    
    public static class OnSettingsUpdate extends AppEvent {}
    public static final OnSettingsUpdate onSettingsUpdate = new OnSettingsUpdate();
    
    public static class OnThemeChange extends AppEvent {}
    public static final OnThemeChange onThemeChange = new OnThemeChange();
}