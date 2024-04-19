package com.lebastudios.sealcode.frontend.fxextends;

import com.lebastudios.sealcode.applogic.FileOperation;
import com.lebastudios.sealcode.applogic.config.Session;
import com.lebastudios.sealcode.applogic.events.AppEvents;
import com.lebastudios.sealcode.exceptions.IllegalNodeCastException;
import com.lebastudios.sealcode.frontend.Dialogs;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class CodeTabPane extends TabPane
{
    public CodeTabPane()
    {
        super();

        addEventHandlers();

        openLastFiles();
    }

    private void addEventHandlers()
    {
        AppEvents.OnAppExit.addListener(this::saveAllFiles);
        
        // Add an event in which, if Ctrl + ANYKEY is pressed
        this.addEventHandler(KeyEvent.KEY_PRESSED, event ->
        {
            if (event.isControlDown() && !event.isShiftDown())
            {
                switch (event.getCode())
                {
                    case KeyCode.S:
                        saveActualTab();
                        break;
                    case KeyCode.N:
                        newFile();
                        break;
                    case KeyCode.O:
                        openFile();
                        break;
                    default:
                }

                event.consume();
            }
        });

        // Add an event in which, if Ctrl + Shift + ANYKEY is pressed
        this.addEventHandler(KeyEvent.KEY_PRESSED, event ->
        {
            if (event.isControlDown() && event.isShiftDown())
            {
                //noinspection SwitchStatementWithTooFewBranches
                switch (event.getCode())
                {
                    case KeyCode.S:
                        saveActualFileAs();
                        break;
                    default:
                }

                event.consume();
            }
        });
    }

    private void openLastFiles()
    {
        List<String> lastFilesPaths = Session.getStaticInstance().filesOpen;

        List<String> auxLastFilesPaths = new ArrayList<>();

        for (String filePath : lastFilesPaths)
        {
            if (filePath.isEmpty()) continue;

            File file = new File(filePath);
            this.getTabs().add(new FormateableTextTab(file));
            auxLastFilesPaths.add(filePath);
        }

        Session.getStaticInstance().filesOpen = auxLastFilesPaths;
    }

    public void saveAllFiles()
    {
        int i = 0;
        List<String> filePaths = Session.getStaticInstance().filesOpen;

        for (var tab : this.getTabs())
        {
            if (i < filePaths.size() && filePaths.get(i) != null
                    && !filePaths.get(i).isEmpty())
            {
                saveFile(tab, new File(filePaths.get(i)));
            }
            else
            {
                if (!Dialogs.confirmationDialog("Save file",
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

    public void saveActualTab()
    {
        Tab actualTab = this.getSelectionModel().getSelectedItem();
        int actualIndex = this.getSelectionModel().getSelectedIndex();

        List<String> filePaths = Session.getStaticInstance().filesOpen;

        if (actualIndex < filePaths.size() && filePaths.get(actualIndex) != null
                && !filePaths.get(actualIndex).isEmpty())
        {
            saveFile(actualTab, new File(filePaths.get(actualIndex)));
        }
        else
        {
            File file = FileOperation.fileChooser().showSaveDialog(null);

            if (file == null)
            {
                return;
            }

            saveFile(actualTab, file.getAbsoluteFile());
        }
    }

    public void newFile()
    {
        Session.getStaticInstance().filesOpen.add("");
        Tab newTab = new FormateableTextTab();
        this.getTabs().add(newTab);
        this.getSelectionModel().select(newTab);
    }

    public void openFile()
    {
        File file = FileOperation.fileChooser().showOpenDialog(null);

        if (file == null)
        {
            return;
        }

        openFile(file);
    }

    public void saveActualFileAs()
    {
        Tab actualTab = this.getSelectionModel().getSelectedItem();
        File file = FileOperation.fileChooser().showSaveDialog(null);

        if (file == null)
        {
            return;
        }

        saveFile(actualTab, file.getAbsoluteFile());
    }

    private void saveFile(Tab fileTab, File file)
    {
        if (fileTab.getContent().getClass() != FormateableText.class)
        {
            throw new IllegalNodeCastException("Tried to save in a file: " + fileTab.getContent().getClass());
        }

        FormateableText txtArea = (FormateableText) fileTab.getContent();

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
            return;
        }

        fileTab.setText(file.getName());

        int index = this.getTabs().indexOf(fileTab);
        final var filesOpen = Session.getStaticInstance().filesOpen;

        if (index > filesOpen.size() - 1)
        {
            filesOpen.add(file.getPath());
        }
        else
        {
            filesOpen.set(index, file.getPath());
        }

    }

    public void openFile(File file)
    {
        file = file.getAbsoluteFile();
        String content;

        try
        {
            content = FileOperation.readFile(file);
        }
        catch (Exception e)
        {
            System.out.println("File isn't readable");
            return;
        }

        Session.getStaticInstance().filesOpen.add(file.getPath());
        Tab newTab = new FormateableTextTab(file.getName(), content, FileOperation.getFileExtension(file));
        this.getTabs().add(newTab);
        this.getSelectionModel().select(newTab);
    }
}
