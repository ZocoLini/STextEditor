package com.lebastudios.sealcode;

import com.lebastudios.sealcode.config.Session;
import com.lebastudios.sealcode.fileobj.EquivalentExtensionsJSON;
import com.lebastudios.sealcode.fileobj.JsonFile;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public final class FileOperation
{
    private static final JsonFile<EquivalentExtensionsJSON> HIGHLIGHTING_RULES = new JsonFile<>(
            new File(FilePaths.getEquivalentExtensionsFile()),
            new EquivalentExtensionsJSON()
    );
    
    public static FileChooser fileChooser()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(Session.getStaticInstance().proyectDirectory));
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

    public static String readResource(String path) throws Exception
    {
        return readFile(new File(SealCodeApplication.class.getResource(path).toURI()));
    }

    public static String readFile(File file) throws Exception
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

    public static void writeFile(File file, String content) throws Exception
    {
        if (file.getParentFile() != null)
        {
            file.getParentFile().mkdirs();
        }

        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.close();
    }
    
    public static String getFileExtensionImg(File file)
    {
        return "ext_" + getFileExtension(file) + ".png";
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


    /**
     * Se encarga de convertir una extensión de archivo a una más común ya definida con la que concuerda en sintaxis
     * para que así pueda ser afectado por el resaltado de sintaxis. Ej.: "xsd" -> "xml"; "iml" -> "xml";
     */
    public static String getEquivalentFileExtension(String extension)
    {
        HIGHLIGHTING_RULES.read();

        return HIGHLIGHTING_RULES.get().equivalentExtensionsMap().getOrDefault(extension, extension);
    }
    
    public static String getEquivalentFileExtension(File file)
    {
        return getEquivalentFileExtension(getFileExtension(file));
    }

    public static String getFileName(File file)
    {
        if (file.isDirectory()) 
        {
            return file.getName();
        }
        
        String fileExtension = getFileExtension(file);
        
        return file.getName().replace("." + fileExtension, "");
    }
}
