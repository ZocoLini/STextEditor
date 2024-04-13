package com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.leftvbox;

import com.lebastudios.stexteditor.exceptions.NotImplementedException;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.ButtonManager;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.MainManager;
import javafx.event.ActionEvent;

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
        super(MainManager.getInstance().botonCompilar);
    }

    @Override
    protected String iconID()
    {
        return "compile.png";
    }

    @Override
    public void onAction(ActionEvent event)
    {
        throw new NotImplementedException();
    }
}
