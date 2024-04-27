package com.lebastudios.sealcode.logic.java.completations;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.lebastudios.sealcode.util.Completation;

public class MethodCompletation extends Completation
{
    private MethodCompletation method;
    
    public MethodCompletation(MethodDeclaration method)
    {
        super(method.getNameAsString(), "method.png");
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
}
