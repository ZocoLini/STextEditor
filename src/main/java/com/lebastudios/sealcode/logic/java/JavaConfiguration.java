package com.lebastudios.sealcode.logic.java;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;

public class JavaConfiguration
{
    private static JavaConfiguration instance;

    public static JavaConfiguration getInstance()
    {
        if (instance == null) instance = new JavaConfiguration();

        return instance;
    }

    private JavaConfiguration()
    {
    }

    private JavaParser javaParser = new JavaParser();
    
    public JavaParser getJavaParser()
    {
        return javaParser;
    }
    
    public void setLangLvl(ParserConfiguration.LanguageLevel langLvl)
    {
        ParserConfiguration conf = javaParser.getParserConfiguration();
        conf.setLanguageLevel(langLvl);
        javaParser = new JavaParser(conf);
    }
}
