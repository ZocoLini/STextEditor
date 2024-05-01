package com.lebastudios.sealcode.logic.java.indexer;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.lebastudios.sealcode.exceptions.NotImplementedException;
import com.lebastudios.sealcode.logic.java.JavaConfiguration;
import com.lebastudios.sealcode.logic.java.completations.ClassOrInterfceCompletation;
import com.lebastudios.sealcode.logic.java.completations.JavaNodeCompletation;
import com.lebastudios.sealcode.util.Completation;
import com.lebastudios.sealcode.util.ILangIndexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

public class JavaIndexer implements ILangIndexer
{
    private static JavaIndexer instance;

    public static JavaIndexer getInstance()
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
    public void index(File file)
    {
        ParseResult<CompilationUnit> result = null;
        try
        {
            result = JavaConfiguration.getInstance().getJavaParser().parse(file);
        } catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }

        if (result.getResult().isEmpty()) return;
        
        CompilationUnit cu = result.getResult().get();
        
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
    public void unIndex(File file)
    {
        throw new NotImplementedException("Unindexing is not implemented yet");
    }

    @Override
    public TreeSet<Completation> getCompletations(String word, File file)
    {
        String wordToProcess = Pattern.compile("\\([^()]*\\)").matcher(word).replaceAll("");
        String substring = wordToProcess;
        JavaNodeCompletation javaNodeCompletation = null;
        
        if (wordToProcess.contains(".")) 
        {
            substring = wordToProcess.substring(0, wordToProcess.indexOf("."));
            javaNodeCompletation = findNode(substring);
            wordToProcess = wordToProcess.replace(substring, "").substring(1);
        }
        
        while (javaNodeCompletation != null && wordToProcess.contains("."))
        {
            substring = wordToProcess.substring(0, wordToProcess.indexOf("."));
            javaNodeCompletation = findNode(substring, javaNodeCompletation.getChildren());
            wordToProcess = wordToProcess.replace(substring, "").substring(1);
        }
        
        if (javaNodeCompletation != null) 
        {
            return filterWord(wordToProcess, javaNodeCompletation.getChildren());
        }
        
        return filterWord(substring);
    }

    private TreeSet<Completation> filterWord(String word)
    {
        List<Completation> completations = new ArrayList<>();

        for (var key : classesAndInterfaces.keySet())
        {
            if (!key.contains(word)) continue;

            completations.add(new ClassOrInterfceCompletation(classesAndInterfaces.get(key)));
        }

        // TODO: filtrar mas cosas que clases
        
        return new TreeSet<>(completations);
    }
    
    private TreeSet<Completation> filterWord(String word, TreeSet<JavaNodeCompletation> childrens)
    {
        List<Completation> completations = new ArrayList<>();
        
        for (var children : childrens)
        {
            if (!children.getValue().contains(word)) continue;
                
            completations.add(children);
        }

        return new TreeSet<>(completations);
    }
    
    public JavaNodeCompletation findNode(String nodeName)
    {
        for (var variable : classesAndInterfaces.keySet())
        {
            String aux = variable.substring(variable.lastIndexOf(".") + 1);
            if (aux.equals(nodeName)) return JavaNodeCompletation.toJavaNode(classesAndInterfaces.get(variable));
        }

        return null;
    }
    
    private JavaNodeCompletation findNode(String node, TreeSet<JavaNodeCompletation> childrens)
    {
        for (var children : childrens)
        {
            if (children.getValue().equals(node)) return children;
        }
        
        return null;
    }
}
