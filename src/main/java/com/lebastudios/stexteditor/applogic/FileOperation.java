package com.lebastudios.stexteditor.applogic;

import com.lebastudios.stexteditor.TextEditorApplication;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public class FileOperation
{
    public static FileChooser fileChooser()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files", "*.*"));
        return fileChooser;
    }
    
    public static DirectoryChooser directoryChooser()
    {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open proyect");
        return directoryChooser;
    }

    public static String read(File file) throws Exception
    {
        if (!file.exists())
        {
            throw new FileNotFoundException();
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

    public static String readResource(String path) throws Exception
    {
        return read(new File(TextEditorApplication.class.getResource(path).toURI()));
    }
    
    public static void write(File file, String content) throws Exception
    {
        if (file.getParentFile() != null)
        {
            file.getParentFile().mkdirs();
        }

        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.close();
    }

    public static String getFileExtension(File file)
    {

        if (file == null)
        {
            throw new IllegalArgumentException("File is null");
        }

        if (!file.exists())
        {
            throw new IllegalArgumentException("File does not exist");
        }

        if (file.isDirectory())
        {
            throw new IllegalArgumentException("File is a directory");
        }

        String fileName = file.getName();
        int index = fileName.lastIndexOf('.');
        if (index == -1)
        {
            return "";
        }
        return fileName.substring(index + 1);
    }
}
