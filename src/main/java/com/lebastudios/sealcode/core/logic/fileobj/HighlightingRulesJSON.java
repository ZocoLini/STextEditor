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
            if (variable.styleClass.equals(name))
            {
                return variable;
            }
        }

        return null;
    }

    public static class HighlightingRule
    {
        public String styleClass;
        public String regex;
        public String highlightRegex;
    }
}
