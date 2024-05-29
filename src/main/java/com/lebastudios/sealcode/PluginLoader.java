package com.lebastudios.sealcode;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class PluginLoader
{
    private static final List<IPlugin> plugins = new ArrayList<>();

    public static void loadPlugins() {
        File[] jars = new File(FilePaths.getPluginDir()).listFiles((dir, name) -> name.endsWith(".jar"));
        if (jars == null) return;

        for (File jar : jars) {
            try {
                URL jarUrl = jar.toURI().toURL();
                URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl}, PluginLoader.class.getClassLoader());

                ServiceLoader<IPlugin> serviceLoader = ServiceLoader.load(IPlugin.class, classLoader);
                for (IPlugin plugin : serviceLoader) {
                    plugin.initialize();
                    plugins.add(plugin);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<IPlugin> getIPlugins() {
        return plugins;
    }
}
