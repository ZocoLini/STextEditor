package com.lebastudios.sealcode.core.logic.fileobj;

import java.util.ArrayList;
import java.util.List;

public class HighlightingRulesJSON
{
    public final List<HighlightingRule> rules = new ArrayList<>();

    public HighlightingRule getHighlightingRule(String name)
    {
        for (var variable : rules)
        {
            if (variable.name.equals(name))
            {
                return variable;
            }
        }

        return null;
    }

    public boolean removeHighlightingRule(String name)
    {
        for (var variable : rules)
        {
            if (variable.name.equals(name))
            {
                rules.remove(variable);
                return true;
            }
        }

        return false;
    }
    
    public static class HighlightingRule
    {
        public String name = "";
        public String styleClass = "";
        public String regex = "";
        public String highlightRegex = "";

        public HighlightingRule()
        {
        }
        
        public HighlightingRule(String name, String styleClass, String regex, String highlightRegex)
        {
            this.name = name;
            this.styleClass = styleClass;
            this.regex = regex;
            this.highlightRegex = highlightRegex;
        }
    }
}
