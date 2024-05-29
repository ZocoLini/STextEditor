package com.lebastudios.plugintest;

import com.lebastudios.sealcode.IPlugin;

public class Plugin implements IPlugin
{
    @Override
    public void initialize() {
        System.out.println("Plugin initialized");
    }
}