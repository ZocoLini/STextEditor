package com.lebastudios.sealcode.frontend.stages.main;

import com.lebastudios.sealcode.applogic.config.Session;
import com.lebastudios.sealcode.frontend.fxextends.*;
import com.lebastudios.sealcode.frontend.stages.settings.SettingsStage;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;

import java.io.File;

public class MainStageController
{
    private static MainStageController instance;

    public static MainStageController getInstance()
    {
        return instance;
    }

    @FXML
    private CodeTabPane codeTabPane;
    @FXML
    private FileSystemTreeView fileSystemTreeView;
    @FXML
    private ConsoleTextArea consoleTextArea;
    @FXML
    private SplitPane horizontalContainer;
    @FXML
    private SplitPane verticalContainer;
    @FXML
    private NotificationsContainer notificationsContainer;

    public MainStageController()
    {
        instance = this;
    }

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

    public void alternateFileSystemVisibility()
    {
        final var proyectFileTreeView = instance.fileSystemTreeView;
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

    public void executeCode()
    {
        addNotification(new Notification("This feature is not implemented in this version."));
    }

    public void compileCode()
    {
        addNotification(new Notification("This feature is not implemented in this version."));
    }

    public void openNewProjectDirectory()
    {
        fileSystemTreeView.openNewProjectDirectory();
    }

    public void newFile()
    {
        instance.codeTabPane.newFile();
    }

    public void openFile()
    {
        instance.codeTabPane.openFile();
    }

    public void openFile(File file)
    {
        instance.codeTabPane.openFile(file);
    }

    public void saveActualTab()
    {
        instance.codeTabPane.saveActualTab();
    }

    public void saveActualFileAs()
    {
        instance.codeTabPane.saveActualFileAs();
    }

    public void openSettings()
    {
        SettingsStage.getInstance().showAndWait();
    }

    public static void addNotification(Notification notification)
    {
        instance.notificationsContainer.addNotification(notification);
    }
}
