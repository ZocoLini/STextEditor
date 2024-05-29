package com.lebastudios.sealcode.fileobj;

import java.util.ArrayList;
import java.util.List;

public class HighlightingRulesJSON
{
    public static final String KEYWORDS_RULE_NAME = "keywordsList";
    
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
    
    public List<String> getKeywords()
    {
        HighlightingRule keywordsRule = getHighlightingRule(KEYWORDS_RULE_NAME);
        
        if (keywordsRule == null) return new ArrayList<>();
        
        String[] keywords = keywordsRule.regex.substring(5, keywordsRule.regex.length() - 3).split("\\|");
        
        return List.of(keywords);
    }
    
    public void setKeywords(String... keywords)
    {
        removeHighlightingRule(KEYWORDS_RULE_NAME);
     
        if (keywords.length == 0) return;
        
        StringBuilder builder = new StringBuilder("\\b(?:");
        
        for (var keyword : keywords)
        {
            builder.append(keyword).append("|");
        }
        
        builder.deleteCharAt(builder.length() - 1);
        
        builder.append(")\\b");
        
        rules.add(new HighlightingRule(
                KEYWORDS_RULE_NAME,
                "keyword",
                builder.toString(),
                ""
        ));
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
