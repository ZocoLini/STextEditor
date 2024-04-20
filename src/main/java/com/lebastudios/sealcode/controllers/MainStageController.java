package com.lebastudios.sealcode.controllers;

import com.lebastudios.sealcode.applogic.config.Session;
import com.lebastudios.sealcode.frontend.Dialogs;
import com.lebastudios.sealcode.frontend.fxextends.*;
import com.lebastudios.sealcode.frontend.fxextends.treeviews.FileSystemTreeCellContent;
import com.lebastudios.sealcode.frontend.fxextends.treeviews.FileSystemTreeItem;
import com.lebastudios.sealcode.frontend.stages.MainStage;
import com.lebastudios.sealcode.frontend.stages.SettingsStage;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainStageController
{
    private static MainStageController instance;

    public static MainStageController getInstance()
    {
        return instance;
    }

    @FXML
    public CodeTabPane codeTabPane;
    @FXML
    public ConsoleTextArea consoleTextArea;
    @FXML
    public SplitPane horizontalContainer;
    @FXML
    public SplitPane verticalContainer;
    @FXML
    public NotificationsContainer notificationsContainer;
    @FXML
    public BorderPane fileSystemBorderPane;

    public MainStageController()
    {
        instance = this;
    }

    @FXML
    public void altrnateNotiVisibility()
    {
        final var notifications = instance.notificationsContainer;
        SplitPane codeTabPaneContainer = instance.horizontalContainer;
        final var items = codeTabPaneContainer.getItems();

        if (!items.remove(notifications))
        {
            items.addLast(notifications);
            codeTabPaneContainer.setDividerPosition(items.indexOf(notifications), 0.80);
        }
    }

    @FXML
    public void altrnateTerminalVisibility()
    {
        final var consoleTextArea = instance.consoleTextArea;
        SplitPane terminalContainer = instance.verticalContainer;
        final var items = terminalContainer.getItems();

        if (!items.remove(consoleTextArea))
        {
            items.add(consoleTextArea);
            terminalContainer.setDividerPositions(0.7);
        }
    }

    @FXML
    public void alternateFileSystemVisibility()
    {
        final var proyectFileTreeView = instance.fileSystemBorderPane;
        SplitPane proyectTreeViewContainer = instance.horizontalContainer;
        final var items = proyectTreeViewContainer.getItems();

        if (!items.remove(proyectFileTreeView))
        {
            items.addFirst(proyectFileTreeView);
            proyectTreeViewContainer.setDividerPosition(
                    items.indexOf(proyectFileTreeView),
                    0.01 * new File(Session.getStaticInstance().proyectDirectory).getName().length()
            );
        }
    }

    @FXML
    public void executeCode()
    {
        notificationsContainer.addNotification(new Notification("This feature is not implemented in this version."));
    }

    @FXML
    public void compileCode()
    {
        notificationsContainer.addNotification(new Notification("This feature is not implemented in this version."));
    }

    @FXML
    public void openNewProjectDirectory()
    {
        FileSystemController.getInstance().fileSystemTreeView.openNewProjectDirectory();
    }
    
    @FXML
    public void openSettings()
    {
        SettingsStage.getInstance().showAndWait();
    }

    @FXML
    void exit()
    {
        Session.getStaticInstance().reset();

        MainStage.getInstance().close();
    }
}
