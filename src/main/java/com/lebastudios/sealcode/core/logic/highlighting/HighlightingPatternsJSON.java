package com.lebastudios.sealcode.core.logic.highlighting;

import java.util.ArrayList;
import java.util.List;

public class HighlightingPatternsJSON
{
    private final List<HighlightingPattern> patternsInfo = new ArrayList<>();

    public List<HighlightingPattern> getPatternsInfo()
    {
        return patternsInfo;
    }

    public HighlightingPattern getPatternInfo(String name)
    {
        for (var variable : patternsInfo)
        {
            if (variable.name.equals(name))
            {
                return variable;
            }
        }

        return null;
    }

    public static class HighlightingPattern
    {
        public String name;
        public String pattern;
        public String colourleablePattern;
    }
}
