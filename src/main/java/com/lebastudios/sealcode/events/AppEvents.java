package com.lebastudios.sealcode.events;

public final class AppEvents
{
    public static final OnAppExit onAppExit = new OnAppExit();
    public static class OnAppExit extends AppEvent {}
    
    public static final OnAppStart onAppStart = new OnAppStart();
    public static class OnAppStart extends AppEvent {}
    
    public static class OnSettingsUpdate extends AppEvent {}
    public static final OnSettingsUpdate onSettingsUpdate = new OnSettingsUpdate();
    
    public static class OnThemeChange extends AppEvent {}
    public static final OnThemeChange onThemeChange = new OnThemeChange();
    
    public static class OnPreferencesUpdate extends AppEvent {}
    public static final OnPreferencesUpdate onPreferencesUpdate = new OnPreferencesUpdate();
}