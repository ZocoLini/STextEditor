package com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.rightvbox;

import com.lebastudios.stexteditor.exceptions.NotImplementedException;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.ButtonManager;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.MainSingletonManager;

public class CompileButtonManager extends ButtonManager
{
    private static CompileButtonManager instance;

    public static CompileButtonManager getInstance()
    {
        if (instance == null) instance = new CompileButtonManager();
        
        return instance;
    }

    private CompileButtonManager()
    {
        super(MainSingletonManager.getInstance().botonCompilar);
        
        instanciated = true;
    }

    @Override
    protected String iconPath()
    {
        return "compile.png";
    }

    @Override
    public void onAction()
    {
        throw new NotImplementedException();
    }

    @Override
    public void loadChilds() {}
}
