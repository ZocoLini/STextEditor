package com.lebastudios.sealcode.core.frontend.fxextends;

import com.lebastudios.sealcode.util.FileOperation;
import com.lebastudios.sealcode.config.Session;
import com.lebastudios.sealcode.controllers.FileSystemController;
import com.lebastudios.sealcode.events.AppEvents;
import com.lebastudios.sealcode.core.frontend.stages.MainStage;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class CodeTabPane extends TabPane
{
    public CodeTabPane()
    {
        super();

        addEventHandlers();
    }

    private void addEventHandlers()
    {
        // Add an event in which, if Ctrl + ANYKEY is pressed
        this.addEventHandler(KeyEvent.KEY_PRESSED, event ->
        {
            if (event.isControlDown() && !event.isShiftDown())
            {
                switch (event.getCode())
                {
                    case KeyCode.S:
                        saveAllFiles();
                    default:
                }

                event.consume();
            }
        });

        AppEvents.onAppExit.addListener(this::saveAllFiles);

        AppEvents.onAppStart.addListener(this::openLastFiles);
        AppEvents.onAppStart.addListener(() ->
        {
            MainStage.getInstance().getScene().focusOwnerProperty().addListener((observable, oldValue, newValue) ->
            {
                if (!newValue.equals(this))
                {
                    saveAllFiles();
                }
            });
        });
    }

    private void saveAllFiles()
    {
        for (Tab tab : this.getTabs())
        {
            ((CodeTab) tab).saveFile();
        }
    }

    private void openLastFiles()
    {
        List<String> auxLastFilesPaths = new ArrayList<>(Session.getStaticInstance().filesOpen);
        
        Session.getStaticInstance().filesOpen = new ArrayList<>();

        for (String filePath : auxLastFilesPaths)
        {
            if (filePath.isEmpty()) continue;

            openFile(new File(filePath));
        }
    }

    public void openFile(FileSystemTreeItem treeItem)
    {
        File file = treeItem.getRepresentingFile();
        
        System.out.println("Se intentará abrir " + file.getAbsolutePath());

        file = file.getAbsoluteFile();
        String content;

        try
        {
            content = FileOperation.readFile(file);
        } catch (Exception e)
        {
            System.out.println("File isn't readable");
            return;
        }

        Tab newTab = new CodeTab(file.getName(), content, treeItem);
        this.getTabs().add(newTab);
        this.getSelectionModel().select(newTab);
        Session.getStaticInstance().filesOpen.add(file.getPath());
    }

    public void openFile(File file)
    {
        var fileTreeItem = FileSystemController.getInstance().fileSystemTreeView.getTreeItemByFile(file);
        
        if (fileTreeItem == null)
        {
            System.err.println("File not found in tree");
            return;
        }
        
        openFile(fileTreeItem);
    }
}
