package com.lebastudios.sealcodeplugins.completations;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.lebastudios.sealcodeplugins.JavaIndexer;

import java.util.TreeSet;

public class MethodCompletation extends JavaNodeCompletation
{
    private final MethodDeclaration method;
    
    public MethodCompletation(MethodDeclaration method)
    {
        super(method.getNameAsString(), "method.png");
        
        this.method = method;
    }

    @Override
    public String getDescription()
    {
        return "Method";
    }

    @Override
    public String getCompletation()
    {
        return getValue() + "()";
    }

    @Override
    public TreeSet<JavaNodeCompletation> getChildren()
    {
        return JavaIndexer.getInstance().findNode(method.getType().asString()).getChildren();
    }
}
