package com.lebastudios.stexteditor.controllers;

import com.lebastudios.stexteditor.app.FileOperation;
import com.lebastudios.stexteditor.app.config.Session;
import com.lebastudios.stexteditor.exceptions.IllegalNodeCastException;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import org.json.JSONArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TabPaneController extends Controller
{
    private static TabPaneController instace;

    public static TabPaneController getInstance() {return instace;}

    public TabPaneController()
    {
        instace = this;
    }

    @FXML
    private TabPane tabPanne;

    @FXML
    private void saveAllFiles()
    {
        int i = 0;
        JSONArray filePaths = Session.getInstance().getFilesOpen();

        for (var tab : tabPanne.getTabs())
        {
            if (i < filePaths.length() && filePaths.getString(i) != null
                    && !filePaths.getString(i).isEmpty())
            {
                saveFile(tab, new File(filePaths.getString(i)));
            }
            else
            {
                saveFile(tab, FileOperation.fileChooser().showSaveDialog(null).getAbsoluteFile());
            }

            i++;
        }

        System.out.println("All files has been saved!!");
    }

    @FXML
    private void saveActualFile()
    {
        Tab actualTab = tabPanne.getSelectionModel().getSelectedItem();
        int actualIndex = tabPanne.getSelectionModel().getSelectedIndex();

        JSONArray filePaths = Session.getInstance().getFilesOpen();

        if (actualIndex < filePaths.length() && filePaths.getString(actualIndex) != null
                && !filePaths.getString(actualIndex).isEmpty())
        {
            saveFile(actualTab, new File(filePaths.getString(actualIndex)));
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
        if (fileTab.getContent().getClass() != TextArea.class)
        {
            throw new IllegalNodeCastException("Tried to save in a file: " + fileTab.getContent().getClass());
        }

        TextArea txtArea = (TextArea) fileTab.getContent();

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
            throw new RuntimeException(e);
        }

        fileTab.setText(file.getName());

        Session.getInstance().getFilesOpen().put(tabPanne.getTabs().indexOf(fileTab), file.getPath());

        return true;
    }

    public void loadLastFiles()
    {
        JSONArray lastFilesPaths = Session.getInstance().getFilesOpen();

        if (lastFilesPaths.isEmpty())
        {
            return;
        }

        for (int i = 0; i < lastFilesPaths.length(); i++)
        {
            File file = new File(lastFilesPaths.getString(i));
            tabPanne.getTabs().add(newWriteableTab(file));
        }
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

        Session.getInstance().getFilesOpen().put(file.getPath());
        tabPanne.getTabs().add(newWriteableTab(file.getName(), content));
    }

    @FXML
    private void newFile()
    {
        Session.getInstance().getFilesOpen().put("");
        tabPanne.getTabs().add(newWriteableTab());
    }

    private Tab newWriteableTab(String name, String content)
    {
        Tab tab = new Tab(name);
        TextArea textArea = new TextArea();
        textArea.setText(content);
        tab.setContent(textArea);

        tab.setOnCloseRequest(event ->
                Session.getInstance().getFilesOpen().remove(
                        tabPanne.getTabs().indexOf(
                                (Tab) event.getTarget()
                        )
                ));

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
}
