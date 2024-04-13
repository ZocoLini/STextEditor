package com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.tabpane;

import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.Linked2MM;
import com.lebastudios.stexteditor.applogic.FileOperation;
import com.lebastudios.stexteditor.applogic.config.global.Session;
import com.lebastudios.stexteditor.exceptions.IllegalNodeCastException;
import com.lebastudios.stexteditor.iobjects.Dialogs;
import com.lebastudios.stexteditor.iobjects.fxextends.FormateableTextTab;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.MainManager;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.SingletonManager;
import com.lebastudios.stexteditor.iobjects.nodes.FormateableText;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class CodeTabPaneManager extends SingletonManager<TabPane>
{
    private static CodeTabPaneManager instance;
    
    public static CodeTabPaneManager getInstance()
    {
        if (instance == null) 
        {
            instance = new CodeTabPaneManager();
        }
        
        return instance;
    }
    
    private CodeTabPaneManager()
    {
        super(MainManager.getInstance().codeTabPane);

        openLastFiles();

    }
    
    @Linked2MM
    public void saveAllFiles()
    {
        int i = 0;
        List<String> filePaths = Session.getStaticInstance().filesOpen;

        for (var tab : managedObject.getTabs())
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

    @Linked2MM
    public void saveActualTab()
    {
        Tab actualTab = managedObject.getSelectionModel().getSelectedItem();
        int actualIndex = managedObject.getSelectionModel().getSelectedIndex();

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

    @Linked2MM
    public void saveActualFileAs()
    {
        Tab actualTab = managedObject.getSelectionModel().getSelectedItem();
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

        int index = managedObject.getTabs().indexOf(fileTab);
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

    private void openLastFiles()
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
            managedObject.getTabs().add(new FormateableTextTab(file));
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
            content = FileOperation.readFile(file);
        }
        catch (Exception e)
        {
            System.out.println("File isnt readable");
            return;
        }

        Session.getStaticInstance().filesOpen.add(file.getPath());
        Tab newTab = new FormateableTextTab(file.getName(), content, FileOperation.getFileExtension(file));
        managedObject.getTabs().add(newTab);
        managedObject.getSelectionModel().select(newTab);
    }
    
    @Linked2MM
    public void openFile()
    {
        File file = FileOperation.fileChooser().showOpenDialog(null);

        if (file == null)
        {
            return;
        }

        openFile(file);
    }

    @Linked2MM
    public void newFile()
    {
        Session.getStaticInstance().filesOpen.add("");
        Tab newTab = new FormateableTextTab();
        managedObject.getTabs().add(newTab);
        managedObject.getSelectionModel().select(newTab);
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
                //noinspection SwitchStatementWithTooFewBranches
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
    public void loadChilds()
    {
        
    }
}
