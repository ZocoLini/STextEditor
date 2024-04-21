package com.lebastudios.sealcode.events;

import com.lebastudios.sealcode.applogic.txtmod.ITextMod;

public class Events
{
    public static class OnTextReplaced extends EventHandler<ITextMod> {}
    public static class OnTextInserted extends EventHandler<ITextMod> {}
    public static class OnTextDeleted extends EventHandler<ITextMod> {}
    
    public static class OnAppExit extends AppEvent {}
    public static class OnAppStart extends AppEvent {}
    public static class OnSettingsUpdate extends AppEvent {}
    public static class OnThemeChange extends AppEvent {}
    public static class OnPreferencesUpdate extends AppEvent {}
    public static class OnProfileChange extends AppEvent {}
}
