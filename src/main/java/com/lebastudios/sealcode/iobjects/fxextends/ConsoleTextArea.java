package com.lebastudios.sealcode.iobjects.fxextends;

import com.lebastudios.sealcode.applogic.config.Session;
import com.lebastudios.sealcode.iobjects.stages.main.MainStageController;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

import java.io.*;

public class ConsoleTextArea extends TextArea
{
    private Process process;
    private BufferedReader reader;
    private OutputStreamWriter writer;

    public ConsoleTextArea()
    {
        super();

        initializeProcess();
        initializeTextArea();
    }

    private void initializeProcess()
    {
        try
        {
            process = new ProcessBuilder("cmd")
                    .directory(new File(Session.getStaticInstance().proyectDirectory))
                    .redirectErrorStream(true).start();
        }
        catch (IOException exception)
        {
            MainStageController.getInstance().fileSystemTreeView.openNewProjectDirectory();

            try
            {
                process = new ProcessBuilder("cmd")
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
        this.setOnKeyPressed(event ->
        {
            if (event.getCode() == KeyCode.ENTER)
            {
                String command = this.getText(primerCaracter, this.getText().length() - 1);
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
            while (process.isAlive())
            {
                if ((line = reader.readLine()) != null)
                {
                    appendTextToConsole(line + "\n");
                    primerCaracter = this.getText().length();
                }

                Thread.sleep(20);
            }
        }
        catch (Exception e)
        {
            System.err.println("Error en el hilo que imprime en el cuadro de consola la salida del proceso " +
                    "Consola");
        }
    }

    private void appendTextToConsole(String text)
    {
        this.appendText(text);
    }
}
