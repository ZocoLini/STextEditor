package com.lebastudios.stexteditor.applogic;

import com.lebastudios.stexteditor.TextEditorApplication;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

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

    public static String readResource(String path) throws Exception
    {
        return readFile(new File(TextEditorApplication.class.getResource(path).toURI()));
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
        
        return toEquivalentFileExtension(fileName.substring(index + 1));
    }

    private static final Map<String, String> extensionMappings = new HashMap<>();

    static {
        // Definir los mapeos de extensiones
        extensionMappings.put("xml", "xml");
        extensionMappings.put("fxml", "xml");
        extensionMappings.put("xsd", "xml");
        extensionMappings.put("iml", "xml");
        extensionMappings.put("xsl", "xml");
        extensionMappings.put("dtd", "xml");

        extensionMappings.put("json", "json");
        extensionMappings.put("jsonc", "json");

        extensionMappings.put("java", "java");
        extensionMappings.put("class", "java");

        extensionMappings.put("c", "c");
        extensionMappings.put("h", "c");

        extensionMappings.put("cpp", "cpp");
        extensionMappings.put("hpp", "cpp");
        extensionMappings.put("cc", "cpp");
        extensionMappings.put("hh", "cpp");
        extensionMappings.put("cxx", "cpp");
        extensionMappings.put("hxx", "cpp");

        extensionMappings.put("py", "py");
        extensionMappings.put("pyc", "py");

        extensionMappings.put("js", "js");
        extensionMappings.put("mjs", "js");

        extensionMappings.put("css", "css");
        extensionMappings.put("scss", "css");
        extensionMappings.put("sass", "css");
        extensionMappings.put("less", "css");
    }
    
    /**
     * Se encarga de convertir una extensión de archivo a una más común ya definida con la que concuerda en sintaxis 
     * para
     * que así pueda ser afectado por el resaltado de sintaxis.
     * Ej.: "xsd" -> "xml"; "iml" -> "xml";
     */
    public static String toEquivalentFileExtension(String extension)
    {
        return extensionMappings.getOrDefault(extension, extension);
    }
}
