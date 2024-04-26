package com.lebastudios.sealcode.logic.inspections;

import com.github.javaparser.StaticJavaParser;
import com.lebastudios.sealcode.util.FileOperation;

import java.io.File;

public class Inspector
{
    public boolean inspect(String codeToInspect)
    {
        try
        {
            StaticJavaParser.parse(codeToInspect);
        }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
            return false;
        }
        
        return true;
    }
    
    public boolean inspect(File file)
    {
        try
        {
            return inspect(FileOperation.readFile(file));
        } catch (Exception e)
        {
            return false;
        }
    }
}
