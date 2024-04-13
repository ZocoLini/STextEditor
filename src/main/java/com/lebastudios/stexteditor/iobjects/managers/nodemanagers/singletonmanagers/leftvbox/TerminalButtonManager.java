package com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.leftvbox;

import com.lebastudios.stexteditor.exceptions.NotImplementedException;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.ButtonManager;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.MainManager;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.treeview.ProyectTreeViewManager;
import javafx.event.ActionEvent;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

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
        super(MainManager.getInstance().botonTerminal);
    }

    @Override
    protected String iconID()
    {
        return "terminal.png";
    }

    @Override
    public void onAction(ActionEvent event)
    {
        final var consoleTextArea = MainManager.getInstance().consoleTextArea;
        SplitPane terminalContainer = MainManager.getInstance().terminalContainer;
        
        if (!terminalContainer.getItems().remove(consoleTextArea)) 
        {
            terminalContainer.getItems().add(consoleTextArea);
            terminalContainer.setDividerPositions(0.7);
        }
    }
}
