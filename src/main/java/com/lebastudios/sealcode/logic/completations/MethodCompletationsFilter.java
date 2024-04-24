package com.lebastudios.sealcode.logic.completations;

import com.lebastudios.sealcode.events.ICompletationsRequest;
import com.lebastudios.sealcode.util.Completation;

import java.io.File;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodCompletationsFilter implements ICompletationsRequest
{
    String methodDeclarationRegex = "\\b(public|private|protected|static|\\s)*\\s*[\\w<>,\\[\\]]+\\s+\\b\\w+\\s*\\([^)]*\\)\\s*(?:throws\\s+[\\w.]+\\s*)?\\{\n";
    String methodNameRegex = "\\("; //Regex to match the method name

    @Override
    public void invoke(TreeSet<Completation> newCompletations, File file, String fileExtension, 
                       String lastWord, String codeAreaText)
    {
        Pattern pattern = Pattern.compile(methodDeclarationRegex);
        Matcher matcher = pattern.matcher(codeAreaText);

        System.out.println(123);
        
        while (matcher.find())
        {
            String method = matcher.group();
            System.out.println(method);
            String methodName = method.split(methodNameRegex)[0].trim();

            methodName = methodName.substring(methodName.lastIndexOf(" ") + 1);
            
            System.out.println(methodName);
            
            newCompletations.add(new MethodCompletation(methodName));
        }
    }
}
