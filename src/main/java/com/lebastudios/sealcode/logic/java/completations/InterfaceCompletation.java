package com.lebastudios.sealcode.logic.java.completations;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.lebastudios.sealcode.util.Completation;

public class InterfaceCompletation extends Completation
{
    private ClassOrInterfaceDeclaration inter;
    
    public InterfaceCompletation(ClassOrInterfaceDeclaration inter)
    {
        super(inter.getNameAsString(), "interface.png");
    }
    @Override
    public String getDescription()
    {
        return "Interface";
    }

    @Override
    public String getCompletation()
    {
        return getValue();
        
    }
}
