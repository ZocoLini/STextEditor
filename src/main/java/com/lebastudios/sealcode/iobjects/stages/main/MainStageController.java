package com.lebastudios.sealcode.iobjects.stages.main;

import com.lebastudios.sealcode.SealCodeApplication;
import com.lebastudios.sealcode.iobjects.fxextends.CodeTabPane;
import com.lebastudios.sealcode.iobjects.fxextends.ConsoleTextArea;
import com.lebastudios.sealcode.iobjects.fxextends.FileSystemTreeView;
import com.lebastudios.sealcode.iobjects.fxextends.NotificationsContainer;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.stage.WindowEvent;

public class MainStageController
{
    private static MainStageController instance;

    public static MainStageController getInstance()
    {
        return instance;
    }

    public MainStageController()
    {
        instance = this;
    }

    /*          Objetos de la interfaz fijos          */
    /**************************************************/
    @FXML public CodeTabPane codeTabPane;
    @FXML public FileSystemTreeView fileSystemTreeView;
    @FXML public ConsoleTextArea consoleTextArea;
    @FXML public SplitPane codeTabPaneContainer;
    @FXML public SplitPane terminalContainer;
    @FXML public NotificationsContainer notificationsContainer;
}
