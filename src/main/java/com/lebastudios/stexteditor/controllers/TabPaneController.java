package com.lebastudios.stexteditor.controllers;

import com.lebastudios.stexteditor.TextEditorApplication;
import com.lebastudios.stexteditor.app.FileOperation;
import com.lebastudios.stexteditor.app.config.Session;
import com.lebastudios.stexteditor.exceptions.IllegalNodeCastException;
import com.lebastudios.stexteditor.nodes.formateableText.FormatteableText;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TabPaneController extends Controller
{
    @FXML
    private TabPane tabPanne;

    @FXML
    private void saveAllFiles()
    {
        int i = 0;
        List<String> filePaths = Session.getInstance().filesOpen;

        for (var tab : tabPanne.getTabs())
        {
            if (i < filePaths.size() && filePaths.get(i) != null
                    && !filePaths.get(i).isEmpty())
            {
                saveFile(tab, new File(filePaths.get(i)));
            }
            else
            {
                if (!AlertsInstanciator.confirmationDialog("Save file",
                        "Do you want to save the file " + tab.getText()
                                + "?"))
                {
                    continue;
                }

                File file = FileOperation.fileChooser().showSaveDialog(null);
                
                if (file == null)
                {
                    continue;
                }
                
                saveFile(tab, file.getAbsoluteFile());
            }

            i++;
        }

        System.out.println("All files has been saved!!");
    }

    @FXML
    private void saveActualTab()
    {
        Tab actualTab = tabPanne.getSelectionModel().getSelectedItem();
        int actualIndex = tabPanne.getSelectionModel().getSelectedIndex();

        List<String> filePaths = Session.getInstance().filesOpen;

        if (actualIndex < filePaths.size() && filePaths.get(actualIndex) != null
                && !filePaths.get(actualIndex).isEmpty())
        {
            saveFile(actualTab, new File(filePaths.get(actualIndex)));
        }
        else
        {
            File file = FileOperation.fileChooser().showSaveDialog(null).getAbsoluteFile();

            if (file == null)
            {
                return;
            }

            saveFile(actualTab, file);
        }
    }

    @FXML
    private void saveActualFileAs()
    {
        Tab actualTab = tabPanne.getSelectionModel().getSelectedItem();

        saveFile(actualTab, FileOperation.fileChooser().showSaveDialog(null).getAbsoluteFile());
    }

    private boolean saveFile(Tab fileTab, File file)
    {
        if (fileTab.getContent().getClass() != FormatteableText.class)
        {
            throw new IllegalNodeCastException("Tried to save in a file: " + fileTab.getContent().getClass());
        }

        FormatteableText txtArea = (FormatteableText) fileTab.getContent();

        try
        {
            if (file.getParentFile() != null)
            {
                file.getParentFile().mkdirs();
            }

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(txtArea.getText());
            fileWriter.close();
        }
        catch (IOException e)
        {
            return false;
        }

        fileTab.setText(file.getName());

        int index = tabPanne.getTabs().indexOf(fileTab);
        final var filesOpen = Session.getInstance().filesOpen;
        
        if (index > filesOpen.size() - 1)
        {
            filesOpen.add(file.getPath());
        }
        else
        {
            filesOpen.set(index, file.getPath());
        }

        return true;
    }

    public void openLastFiles()
    {
        List<String> lastFilesPaths = Session.getInstance().filesOpen;
        
        List<String> auxLastFilesPaths = new ArrayList<>();

        for (String filePath : lastFilesPaths)
        {
            if (filePath.isEmpty())
            {
                continue;
            }

            File file = new File(filePath);
            tabPanne.getTabs().add(newWriteableTab(file));
            auxLastFilesPaths.add(filePath);
        }
        
        Session.getInstance().filesOpen = auxLastFilesPaths;
    }

    @FXML
    private void openFile()
    {
        File file = FileOperation.fileChooser().showOpenDialog(null).getAbsoluteFile();

        if (file == null)
        {
            return;
        }

        String content;

        try
        {
            content = FileOperation.read(file);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        Session.getInstance().filesOpen.add(file.getPath());
        tabPanne.getTabs().add(newWriteableTab(file.getName(), content));
    }

    @FXML
    private void newFile()
    {
        Session.getInstance().filesOpen.add("");
        tabPanne.getTabs().add(newWriteableTab());
    }

    private Tab newWriteableTab(String name, String content)
    {
        Tab tab = new Tab(name);
        FormatteableText formatteableText = new FormatteableText(content);
        tab.setContent(formatteableText);
        
        tab.setOnCloseRequest(event ->
                Session.getInstance().filesOpen.remove(
                        tabPanne.getTabs().indexOf(
                                (Tab) event.getTarget()
                        )
                )
        );
        
        return tab;
    }

    private Tab newWriteableTab(File file)
    {
        String fileName = file.getName();
        String content;

        try
        {
            content = FileOperation.read(file);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return newWriteableTab(fileName, content);
    }

    private Tab newWriteableTab()
    {
        return newWriteableTab("New file", "");
    }

    @Override
    protected void start()
    {
        Stage stage = TextEditorApplication.getStage();
        
        stage.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> openLastFiles());
        stage.addEventHandler(WindowEvent.WINDOW_HIDING, event -> saveAllFiles());
    }
    
    private void addEventHandlers()
    {
        // Add an event in which, if Ctrl + S is pressed, the file is saved
        tabPanne.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() 
                    && event.getCode().equals(javafx.scene.input.KeyCode.S)
                    && !event.isShiftDown()) {
                saveActualTab();
            }
        });
    }
}
