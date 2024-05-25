package com.lebastudios.plugintest;

import com.lebastudios.sealcode.core.logic.IPlugin;
import com.lebastudios.sealcode.events.AppEvents;

public class Plugin implements IPlugin
{
    @Override
    public void initialize()
    {
        AppEvents.onAppStart.addListener(() -> System.out.println("Hello from the plugin!"));
    }
}