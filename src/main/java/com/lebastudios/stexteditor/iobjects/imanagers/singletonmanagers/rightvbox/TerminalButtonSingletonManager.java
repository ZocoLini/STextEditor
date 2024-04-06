package com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.rightvbox;

import com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.MainSingletonManager;
import com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.SingletonManager;
import javafx.scene.control.Button;

public class TerminalButtonSingletonManager extends SingletonManager<Button>
{
    private static TerminalButtonSingletonManager instance;

    public static TerminalButtonSingletonManager getInstance()
    {
        if (instance == null) instance = new TerminalButtonSingletonManager();

        return instance;
    }

    private TerminalButtonSingletonManager()
    {
        super(MainSingletonManager.getInstance().botonTerminal);

        instanciated = true;
    }

    @Override
    public void loadChilds()
    {
        
    }
}
