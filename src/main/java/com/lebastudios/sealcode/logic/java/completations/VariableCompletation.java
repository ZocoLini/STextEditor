package com.lebastudios.sealcode.logic.java.completations;

import com.github.javaparser.ast.body.VariableDeclarator;

import java.util.TreeSet;

public class VariableCompletation extends JavaNodeCompletation
{
    private final VariableDeclarator var;
    
    public VariableCompletation(VariableDeclarator var)
    {
        super(var.getNameAsString(), "variable.jpg");
        
        this.var = var;
    }

    @Override
    public String getDescription()
    {
        return "Field";
    }

    @Override
    public String getCompletation()
    {
        return getValue();
    }

    @Override
    public TreeSet<JavaNodeCompletation> getChildren()
    {
        return JavaNodeCompletation.toJavaNode(var.getType()).getChildren();
    }
}