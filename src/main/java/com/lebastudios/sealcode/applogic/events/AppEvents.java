package com.lebastudios.sealcode.applogic.events;

public final class AppEvents
{
    public static final OnAppExit ON_APP_EXIT = new OnAppExit();

    public static class OnAppExit extends AppEvent {}
}