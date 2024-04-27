package com.lebastudios.sealcode.logic.java.indexer;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.lebastudios.sealcode.logic.java.JavaConfiguration;
import com.lebastudios.sealcode.logic.java.completations.ClassCompletation;
import com.lebastudios.sealcode.logic.java.completations.FieldCompletation;
import com.lebastudios.sealcode.logic.java.completations.MethodCompletation;
import com.lebastudios.sealcode.util.Completation;
import com.lebastudios.sealcode.util.ILangIndexer;

import java.io.File;
import java.util.*;

class JavaIndexer implements ILangIndexer
{
    private static ILangIndexer instance;

    static ILangIndexer getInstance()
    {
        if (instance == null)
        {
            instance = new JavaIndexer();
        }

        return instance;
    }

    private JavaIndexer() {}

    // El String es la ruta del paquete + . + Clase dentro de la que se encuentra en caso de ser inner
    private final Map<String, ClassOrInterfaceDeclaration> classesAndInterfaces = new HashMap<>();
    private final Map<String, RecordDeclaration> records = new HashMap<>();
    private final Map<String, EnumDeclaration> enums = new HashMap<>();
    private final Map<String, AnnotationDeclaration> annotations = new HashMap<>();

    @Override
    public void index(String string)
    {
        ParseResult<CompilationUnit> result = JavaConfiguration.getInstance().getJavaParser().parse(string);
        
        CompilationUnit cu = result.getResult().get();
        
        if (!result.isSuccessful()) return;

        String packageName = "";

        if (cu.getPackageDeclaration().isPresent())
        {
            packageName = cu.getPackageDeclaration().get().getNameAsString();
        }

        for (var typeDeclaration : cu.getTypes())
        {
            index(typeDeclaration, packageName);
        }
    }

    private void index(TypeDeclaration<?> type, String identifier)
    {
        if (!identifier.isEmpty()) 
        {
            identifier = identifier + ".";
        }
        final String newidentifier = identifier + type.getName();
        
        switch (type)
        {
            case ClassOrInterfaceDeclaration classOrInterfaceDeclaration ->
            {
                System.out.println("Indexing class or interface: " + classOrInterfaceDeclaration.getNameAsString()
                        + " " + newidentifier);

                classesAndInterfaces.put(newidentifier, classOrInterfaceDeclaration);
            }
            case RecordDeclaration recordDeclaration ->
            {
                System.out.println("Indexing record: " + recordDeclaration.getNameAsString()
                        + " " + newidentifier);

                records.put(newidentifier, recordDeclaration);
            }
            case EnumDeclaration enumDeclaration ->
            {
                System.out.println("Indexing enum: " + enumDeclaration.getNameAsString()
                        + " " + newidentifier);

                enums.put(newidentifier, enumDeclaration);
            }
            case AnnotationDeclaration annotationDeclaration ->
            {
                System.out.println("Indexing annotation: " + annotationDeclaration.getNameAsString()
                        + " " + newidentifier);

                annotations.put(newidentifier, annotationDeclaration);
            }
            default -> {}
        }
        
        type.getMembers().forEach(bodyDeclaration -> index(bodyDeclaration, newidentifier));
    }

    private void index(BodyDeclaration<?> bodyDeclaration, String identifier)
    {
        if (!(bodyDeclaration instanceof TypeDeclaration<?> type)) return;

        index(type, identifier);
    }

    @Override
    public void unIndex(String string)
    {
        // TODO
    }

    @Override
    public TreeSet<Completation> getCompletations(String word, File file)
    {
        List<Completation> completations = new ArrayList<>();
        
        if (!word.contains(".")) return filterWord(word);
        
        String[] callSequence = word.split("\\.");
        
        ClassOrInterfaceDeclaration obj = getClassOrInterface(callSequence[0]);
        
        if (obj != null) 
        {
            return classOrIntrfaceCompletations(obj, callSequence.length > 1 ? callSequence[1] : "");
        }
        
        return new TreeSet<>(completations);
    }
    
    private TreeSet<Completation> filterWord(String word)
    {
        List<Completation> completations = new ArrayList<>();
        
        for (var variable : classesAndInterfaces.keySet())
        {
            if (!variable.contains(word)) continue;
                
            completations.add(new ClassCompletation(classesAndInterfaces.get(variable)));
        }
        
        // TODO: Add enums, annotations and records

        return new TreeSet<>(completations);
    }
    
    private ClassOrInterfaceDeclaration getClassOrInterface(String name)
    {
        for (var variable : classesAndInterfaces.keySet())
        {
            String aux = variable.substring(variable.lastIndexOf(".") + 1);
            if (aux.equals(name)) return classesAndInterfaces.get(variable);
        }
        
        return null;
    }
    
    private TreeSet<Completation> classOrIntrfaceCompletations(ClassOrInterfaceDeclaration obj, String wordToComplete)
    {
        List<Completation> completations = new ArrayList<>();
        
        for (var variable : obj.getMethods())
        {
            if (variable.getNameAsString().contains(wordToComplete)) 
            {
                completations.add(new MethodCompletation(variable));
            }
        }
        
        for (var field : obj.getFields())
        {
            for (var variable : field.getVariables())
            {
                System.out.println(variable.getName());
                if (variable.getNameAsString().contains(wordToComplete))
                {
                    completations.add(new FieldCompletation(variable));
                }
            }
        }
        
        return new TreeSet<>(completations); 
    }
}
