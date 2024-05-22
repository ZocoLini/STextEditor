package com.lebastudios.sealcode.core.logic.fileobj;

import java.util.ArrayList;
import java.util.List;

// TODO: Clase abstracta que cargue estos objetos y su package

public class EquivalentExtensionsJSON
{
    public List<EquivalentExtensions> filter = new ArrayList<>();

    public static class EquivalentExtensions
    {
        public String extensions;
        public List<String> equivalentExtensions = new ArrayList<>();
    }
}
