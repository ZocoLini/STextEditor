package com.lebastudios.stexteditor.events;

public class GlobalEvents
{
    public static class OnUpdate extends AppEvent {}
    public static OnUpdate onUpdate = new OnUpdate();
    
    public static class OnThemeChanged extends AppEvent {}
    public static OnThemeChanged onThemeChanged = new OnThemeChanged();
}
