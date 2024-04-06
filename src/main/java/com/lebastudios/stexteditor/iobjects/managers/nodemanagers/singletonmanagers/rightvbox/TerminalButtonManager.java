package com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.rightvbox;

import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.ButtonManager;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.MainSingletonManager;

public class TerminalButtonManager extends ButtonManager
{
    private static TerminalButtonManager instance;

    public static TerminalButtonManager getInstance()
    {
        if (instance == null) instance = new TerminalButtonManager();

        return instance;
    }

    private TerminalButtonManager()
    {
        super(MainSingletonManager.getInstance().botonTerminal);

        instanciated = true;
    }

    @Override
    protected String iconPath()
    {
        return "terminal.png";
    }

    @Override
    public void onAction()
    {

    }

    @Override
    public void loadChilds()
    {
        
    }
}
