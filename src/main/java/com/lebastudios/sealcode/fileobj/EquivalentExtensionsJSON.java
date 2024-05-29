package com.lebastudios.sealcode.fileobj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: Clase abstracta que cargue estos objetos y su package

public class EquivalentExtensionsJSON
{
    public List<EquivalentExtensions> filter = new ArrayList<>();

    public List<String> getEquivalentExtensionsFor(String extension)
    {
        for (var filter : filter)
        {
            if (filter.extension.equals(extension)) return filter.equivalentExtensions;
        }
        
        return null;
    }

    public Map<String, String> equivalentExtensionsMap()
    {
        Map<String, String> mappedEqExt = new HashMap<>();

        for (var filter : filter)
        {
            for (var equivalentExtension : filter.equivalentExtensions)
            {
                mappedEqExt.put(equivalentExtension, filter.extension);
            }
        }

        return mappedEqExt;
    }
    
    public static class EquivalentExtensions
    {
        public String extension = "";
        public List<String> equivalentExtensions = new ArrayList<>();
        
        public EquivalentExtensions() {}

        public EquivalentExtensions(String extension)
        {
            this.extension = extension;
        }
    }
}
