package com.lebastudios.sealcode.controllers;

import com.lebastudios.sealcode.frontend.*;
import com.lebastudios.sealcode.config.Session;
import com.lebastudios.sealcode.frontend.stages.MainStage;
import com.lebastudios.sealcode.frontend.stages.SettingsStage;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.File;

public class MainStageController implements IStageController
{
    private static MainStageController instance;

    public static MainStageController getInstance()
    {
        return instance;
    }
    
    @FXML
    private CodeTabPane codeTabPane;
    @FXML
    private ConsoleTextArea consoleTextArea;
    @FXML
    private SplitPane horizontalContainer;
    @FXML
    private SplitPane verticalContainer;
    @FXML
    private NotificationsContainer notificationsContainer;
    @FXML
    private BorderPane fileSystemBorderPane;
    @FXML
    private StackPane codeEditorStackPane;

    public MainStageController()
    {
        instance = this;
    }

    @FXML
    public void alternateNotiVisibility()
    {
        final var notifications = notificationsContainer;
        SplitPane codeTabPaneContainer = horizontalContainer;
        final var items = codeTabPaneContainer.getItems();

        if (!items.remove(notifications))
        {
            items.addLast(notifications);
            codeTabPaneContainer.setDividerPosition(items.indexOf(notifications), 0.80);
        }
    }

    private void showNotifications()
    {
        final var notifications = notificationsContainer;
        SplitPane codeTabPaneContainer = horizontalContainer;
        final var items = codeTabPaneContainer.getItems();

        if (!items.contains(notifications))
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
        FileSystemController.getInstance().openNewProjectDirectory();
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

    public void addNotification(Notification notification)
    {
        notificationsContainer.addNotification(notification);
        showNotifications();
    }

    public void openFile(FileSystemTreeItem treeItem)
    {
        codeTabPane.openFile(treeItem);
    }
    
    @Override
    public void customiceStage()
    {
        
    }
}
