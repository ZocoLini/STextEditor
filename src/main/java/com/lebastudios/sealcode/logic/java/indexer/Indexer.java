package com.lebastudios.sealcode.logic.java.indexer;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.lebastudios.sealcode.config.Session;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;

public class Indexer
{ 
    private Indexer() {}
    
    public static void index()
    {
        try
        {
            CompilationUnit cu = StaticJavaParser.parse(new File(Session.getStaticInstance().proyectDirectory + "/src/Main.java"));
            Optional<ClassOrInterfaceDeclaration> classA = cu.getClassByName("Main");
            ClassOrInterfaceDeclaration classB = classA.get();
            System.out.println(classB.getMethods().get(0).getName());
        } catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }
}
