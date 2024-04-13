package com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers;

import com.lebastudios.stexteditor.applogic.config.global.Session;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.treeview.ProyectTreeViewManager;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

import java.io.*;

public class ConsoleTextAreaManager extends SingletonManager<TextArea>
{
    private static ConsoleTextAreaManager instance;

    public static ConsoleTextAreaManager getInstance()
    {
        if (instance == null) instance = new ConsoleTextAreaManager();
        return instance;
    }

    private Process process;
    private BufferedReader reader;
    private OutputStreamWriter writer;

    public ConsoleTextAreaManager()
    {
        super(MainManager.getInstance().consoleTextArea);

        initializeProcess();
        initializeTextArea();
    }

    private void initializeProcess()
    {
        try
        {
            process = new ProcessBuilder("bash")
                    .directory(new File(Session.getStaticInstance().proyectDirectory))
                    .redirectErrorStream(true).start();
        }
        catch (IOException exception)
        {
            ProyectTreeViewManager.getInstance().openNewProjectDirectory();

            try
            {
                process = new ProcessBuilder("bash")
                        .directory(new File(Session.getStaticInstance().proyectDirectory))
                        .redirectErrorStream(true).start();
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }

        reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        writer = new OutputStreamWriter(process.getOutputStream());
        
        Thread hilo = new Thread(this::readOutputStream);
        hilo.setDaemon(true);
        hilo.start();
    }

    private void initializeTextArea()
    {
        managedObject.setOnKeyPressed(event ->
        {
            if (event.getCode() == KeyCode.ENTER)
            {
                String command = managedObject.getText(primerCaracter, managedObject.getText().length() - 1);
                executeCommand(command + "\n");
                event.consume();
            }
        });
    }

    int primerCaracter = 0;
    
    private void executeCommand(String command)
    {
        try
        {
            writer.write(command);
            writer.flush();
        }
        catch (IOException e)
        {

            System.err.println("Error al ejecutar un comando en la ventana de consola.");
        }
    }

    private void readOutputStream()
    {
        String line;
        try
        {
            while ((line = reader.readLine()) != null)
            {
                appendTextToConsole(line + "\n");
                primerCaracter = managedObject.getText().length();
            }
        }
        catch (IOException e)
        {
            System.err.println("Error en el hilo que imprime en el cuadro de consola la salida del proceso " +
                    "Consola");
        }
    }
    
    private void appendTextToConsole(String text)
    {
        managedObject.appendText(text);
    }

    @Override
    protected void loadChilds() {}
}
