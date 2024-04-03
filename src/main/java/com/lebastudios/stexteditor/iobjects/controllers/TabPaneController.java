package com.lebastudios.stexteditor.iobjects.controllers;

import com.lebastudios.stexteditor.annotations.Linked2MC;
import com.lebastudios.stexteditor.applogic.FileOperation;
import com.lebastudios.stexteditor.applogic.config.Session;
import com.lebastudios.stexteditor.exceptions.IllegalNodeCastException;
import com.lebastudios.stexteditor.iobjects.AlertsInstanciator;
import com.lebastudios.stexteditor.applogic.txtformatter.StyleSetter;
import com.lebastudios.stexteditor.iobjects.nodes.FormateableText;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class TabPaneController extends Controller
{
    private static TabPaneController instance;
    
    public static TabPaneController getInstance()
    {
        if (instance == null) 
        {
            instance = new TabPaneController();
        }
        
        return instance;
    }
    
    private TabPaneController()
    {
        super();
    }
    
    private static final TabPane tabPane = MainController.getInstance().tabPane;
    
    @Linked2MC
    public void saveAllFiles()
    {
        int i = 0;
        List<String> filePaths = Session.getStaticInstance().filesOpen;

        for (var tab : tabPane.getTabs())
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

    @Linked2MC
    public void saveActualTab()
    {
        Tab actualTab = tabPane.getSelectionModel().getSelectedItem();
        int actualIndex = tabPane.getSelectionModel().getSelectedIndex();

        List<String> filePaths = Session.getStaticInstance().filesOpen;

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

    @Linked2MC
    public void saveActualFileAs()
    {
        Tab actualTab = tabPane.getSelectionModel().getSelectedItem();
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

        int index = tabPane.getTabs().indexOf(fileTab);
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

    public void openLastFiles()
    {
        List<String> lastFilesPaths = Session.getStaticInstance().filesOpen;
        
        List<String> auxLastFilesPaths = new ArrayList<>();

        for (String filePath : lastFilesPaths)
        {
            if (filePath.isEmpty())
            {
                continue;
            }

            File file = new File(filePath);
            tabPane.getTabs().add(newWriteableTab(file));
            auxLastFilesPaths.add(filePath);
        }
        
        Session.getStaticInstance().filesOpen = auxLastFilesPaths;
    }

    public void openFile(File file)
    {
        file = file.getAbsoluteFile();
        String content;

        try
        {
            content = FileOperation.read(file);
        }
        catch (Exception e)
        {
            System.out.println("File isnt readable");
            return;
        }

        Session.getStaticInstance().filesOpen.add(file.getPath());
        Tab newTab = newWriteableTab(file.getName(), content, FileOperation.getFileExtension(file));
        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab);
    }
    
    @Linked2MC
    public void openFile()
    {
        File file = FileOperation.fileChooser().showOpenDialog(null);

        if (file == null)
        {
            return;
        }

        openFile(file);
    }

    @Linked2MC
    public void newFile()
    {
        Session.getStaticInstance().filesOpen.add("");
        Tab newTab = newWriteableTab();
        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab);
    }

    private Tab newWriteableTab(String name, String content, String fileExtension)
    {
        Tab tab = new Tab(name);
        FormateableText formateableText = new FormateableText(content);

        tab.setContent(formateableText);
        
        StyleSetter.defaultStyle(formateableText, fileExtension);
        
        tab.setOnCloseRequest(event ->
                Session.getStaticInstance().filesOpen.remove(
                        tabPane.getTabs().indexOf(
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
            System.err.println("File not found, probably deleted");
            content = "";
        }

        return newWriteableTab(fileName, content, FileOperation.getFileExtension(file));
    }

    private Tab newWriteableTab()
    {
        return newWriteableTab("New file.txt", "", "txt");
    }
    
    @Override
    protected void addEventHandlers()
    {
        // Add an event in which, if Ctrl + ANYKEY is pressed
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && !event.isShiftDown()) {
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
            }
        });
        // Add an event in which, if Ctrl + Shift + ANYKEY is pressed
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && event.isShiftDown()) {
                switch (event.getCode()) 
                {
                    case KeyCode.S:
                        saveActualFileAs();
                        break;
                    default:
                }
            }
        });
    }

    @Override
    protected void onThemeChangue()
    {
        // TODO: Hacer que funcione
        tabPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        tabPane.setStyle("-fx-background-color: green");
    }
}
