package com.lebastudios.sealcode.logic.java.completations;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.lebastudios.sealcode.util.Completation;

import java.util.TreeSet;

public abstract class JavaNodeCompletation extends Completation
{
    public JavaNodeCompletation(String value, String iconName)
    {
        super(value, iconName);
    }
    
    public static JavaNodeCompletation toJavaNode(Node node)
    {
        if (node instanceof MethodDeclaration methodDeclaration) 
        {
            return new MethodCompletation(methodDeclaration);
        }
        if (node instanceof ClassOrInterfaceDeclaration classe) 
        {
            return new ClassOrInterfceCompletation(classe);
        }
        if (node instanceof VariableDeclarator variableDeclarator) 
        {
            return new VariableCompletation(variableDeclarator);
        }
        
        if (node instanceof FieldDeclaration) return null;

        System.err.println("Class: " + node.getClass() + " is not supported.");
        return null;
    }
    
    public abstract TreeSet<JavaNodeCompletation> getChildren();
}
