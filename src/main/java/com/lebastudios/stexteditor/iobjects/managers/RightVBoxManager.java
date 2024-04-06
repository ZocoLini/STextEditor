package com.lebastudios.stexteditor.iobjects.managers;

import com.lebastudios.stexteditor.OSManager.console.ComConsole;
import com.lebastudios.stexteditor.annotations.Linked2MM;
import com.lebastudios.stexteditor.applogic.config.Session;
import javafx.scene.layout.VBox;

import java.io.File;

public class RightVBoxManager extends Manager<VBox>
{
    private static RightVBoxManager instance;
    
    public static RightVBoxManager getInstance()
    {
        if (instance == null) 
        {
            instance = new RightVBoxManager();
        }
        
        return instance;
    }
    
    private RightVBoxManager()
    {
        super(MainManager.getInstance().rightVBox);
        
        instanciated = true;
    }
    
    @Linked2MM
    public void compile()
    {
        File proyectDirectory = new File(Session.getStaticInstance().proyectDirectory);
        String archivoMain = "\"" + proyectDirectory.getAbsolutePath() + "\\src\\Main.java";
        String directorioSalida = proyectDirectory.getAbsolutePath() + "\\out";
        System.out.println(archivoMain);
        ComConsole.executeCommand("javac -d " + directorioSalida + " " + archivoMain);
    }
    
    @Linked2MM
    public void execute()
    {
        compile();
    }
}
