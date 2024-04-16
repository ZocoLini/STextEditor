package com.lebastudios.sealcode.iobjects.fxextends.buttons;

import com.lebastudios.sealcode.iobjects.stages.main.MainStageController;
import javafx.event.ActionEvent;
import javafx.scene.control.SplitPane;

public class TerminalButton extends ButtonManager
{
    @Override
    protected String iconID()
    {
        return "terminal.png";
    }

    @Override
    public void onAction(ActionEvent event)
    {
        final var consoleTextArea = MainStageController.getInstance().consoleTextArea;
        SplitPane terminalContainer = MainStageController.getInstance().terminalContainer;
        
        if (!terminalContainer.getItems().remove(consoleTextArea)) 
        {
            terminalContainer.getItems().add(consoleTextArea);
            terminalContainer.setDividerPositions(0.7);
        }
    }
}
