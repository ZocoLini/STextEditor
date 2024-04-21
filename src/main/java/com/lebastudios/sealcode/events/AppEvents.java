package com.lebastudios.sealcode.events;

import com.lebastudios.sealcode.events.Events.*;

public final class AppEvents
{
    public static final OnAppExit onAppExit = new OnAppExit();
    
    public static final OnAppStart onAppStart = new OnAppStart();
    
    public static final OnSettingsUpdate onSettingsUpdate = new OnSettingsUpdate();
    
    public static final OnThemeChange onThemeChange = new OnThemeChange();
    
    public static final OnPreferencesUpdate onPreferencesUpdate = new OnPreferencesUpdate();
    
    public static final OnProfileChange onProfileChange = new OnProfileChange();
}