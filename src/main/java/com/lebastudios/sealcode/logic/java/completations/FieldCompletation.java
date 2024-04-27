package com.lebastudios.sealcode.logic.java.completations;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.lebastudios.sealcode.util.Completation;

public class FieldCompletation extends Completation
{
    private VariableDeclarator var;
    public FieldCompletation(VariableDeclarator var)
    {
        super(var.getNameAsString(), "variable.jpg");
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
}
