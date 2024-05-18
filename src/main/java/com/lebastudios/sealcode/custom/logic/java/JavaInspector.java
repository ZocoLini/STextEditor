package com.lebastudios.sealcode.custom.logic.java;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.lebastudios.sealcode.core.logic.IInspector;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;

public class JavaInspector implements IInspector
{
    private static IInspector instance;
    
    public static IInspector getInstance()
    {
        if (instance == null) instance = new JavaInspector();
        
        return instance;
    }
    
    private JavaInspector() {}
    
    @Override
    public StyleSpans<Collection<String>> inspect(String text, String fileExtension)
    {
        // TODO: que funcione el aplicar este estilo.
        text = text.replace("\n", "");
        ParseResult<CompilationUnit> result = JavaConfiguration.getInstance().getJavaParser().parse(text);

        if (result.isSuccessful()) return null;

        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        
        int lastIndex = 0;
        
        for (var problem : result.getProblems())
        {
            if (problem.getLocation().isEmpty()) return null;
            
            final var problemLocation = problem.getLocation().get().getBegin().getRange().get();
            
            spansBuilder.add(Collections.singleton(""), problemLocation.begin.column - lastIndex);
            spansBuilder.add(Collections.singleton("inspectionError"), problemLocation.end.column - problemLocation.begin.column);
            lastIndex = problemLocation.end.column;
        }
        
        return spansBuilder.create();
    }
}
