package com.lebastudios.stexteditor.app;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileOperation
{
    public static String read(File file) throws Exception
    {
        if (!file.exists()) 
        {
            return "";
        }
        
        FileReader reader = new FileReader(file);

        int caracter = reader.read();
        StringBuilder content = new StringBuilder();

        while (caracter != -1)
        {
            content.append((char) caracter);
            caracter = reader.read();
        }
        
        reader.close();
        return content.toString();
    }
    
    public static boolean write(File file, String content) throws Exception
    {
        if (file.getParentFile() != null) 
        {
            file.getParentFile().mkdirs();
        }
        
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.close();
        
        return true;
    }
}
