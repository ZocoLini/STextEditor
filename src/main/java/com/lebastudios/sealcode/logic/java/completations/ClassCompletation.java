package com.lebastudios.sealcode.logic.java.completations;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.lebastudios.sealcode.util.Completation;

public class ClassCompletation extends Completation
{
    private ClassOrInterfaceDeclaration classe;
    
    public ClassCompletation(ClassOrInterfaceDeclaration classe)
    {
        super(classe.getNameAsString(), "class.jpg");
    }

    @Override
    public String getDescription()
    {
        return "Class";
    }

    @Override
    public String getCompletation()
    {
        return getValue();
    }
}
