package com.lebastudios.sealcode.logic.completations;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.lebastudios.sealcode.events.ICompletationsRequest;
import com.lebastudios.sealcode.util.Completation;
import com.lebastudios.sealcode.util.FileOperation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.TreeSet;

public class MethodCompletationsFilter implements ICompletationsRequest
{
    @Override
    public void invoke(TreeSet<Completation> newCompletations, File file, String fileExtension, 
                       String lastWord, String codeAreaText)
    {
        if (!fileExtension.equals("java")) return;

        CompilationUnit cu;
        
        try
        {
            cu = StaticJavaParser.parse(codeAreaText);
            new Thread(() ->
            {
                try
                {
                    FileOperation.writeFile(file, codeAreaText);
                } catch (Exception e)
                {
                    throw new RuntimeException(e);
                }
            }).start();
        }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
            cu = null;
        }

        try
        {
            cu = StaticJavaParser.parse(file);
        } catch (ParseProblemException e)
        {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        
        Optional<ClassOrInterfaceDeclaration> classA = cu.getClassByName("Main");
        
        newCompletations.addAll(classA.get().getMethods().stream().map(method -> 
                new MethodCompletation(method.getNameAsString())).toList());
        
        newCompletations.addAll(classA.get().getFields().stream().map(field ->
                new FieldCompletation(field.getVariable(0).getNameAsString())).toList());
    }
}
