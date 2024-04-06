package com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.rightvbox;

import com.lebastudios.stexteditor.annotations.Linked2MM;
import com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.MainSingletonManager;
import com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.SingletonManager;
import javafx.scene.layout.VBox;

public class RightVBoxSingletonManager extends SingletonManager<VBox>
{
    private static RightVBoxSingletonManager instance;
    
    public static RightVBoxSingletonManager getInstance()
    {
        if (instance == null) 
        {
            instance = new RightVBoxSingletonManager();
        }
        
        return instance;
    }
    
    private RightVBoxSingletonManager()
    {
        super(MainSingletonManager.getInstance().rightVBox);
        
        instanciated = true;
    }
    
    @Linked2MM
    public void compile()
    {
        System.out.println("Compilando...");
    }
    
    @Linked2MM
    public void execute()
    {
        compile();
        System.out.println("Ejecutando...");
    }

    @Override
    public void loadChilds()
    {
        CompileButtonSingletonManager.getInstance().load();
        ExecuteButtonSingletonManager.getInstance().load();
        TerminalButtonSingletonManager.getInstance().load();
    }
}
