package com.lebastudios.stexteditor.interfacecontrollers;

import com.lebastudios.stexteditor.TextEditorApplication;
import com.lebastudios.stexteditor.app.FileOperation;
import com.lebastudios.stexteditor.app.config.Session;
import com.lebastudios.stexteditor.exceptions.IllegalNodeCastException;
import com.lebastudios.stexteditor.interfacecontrollers.proyecttreeview.CustomTreeCell;
import com.lebastudios.stexteditor.interfacecontrollers.proyecttreeview.TreeObjectController;
import com.lebastudios.stexteditor.nodes.formateableText.FormateableText;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class TabPaneController extends Controller
{
    // TODO: Crear un controlador principal qu instancie controlladores secndarios para cada objeto que necesite
    //  ser controlado. Todos heredarán de Controller
    
    private static TabPaneController instance;
    
    public static TabPaneController getInstance()
    {
        return instance;
    }
    
    public TabPaneController()
    {
        super();
        instance = this;
    }
    
    @FXML
    public TabPane tabPane;
    
    @FXML
    public TreeView<TreeObjectController> treeView;

    @FXML
    private void exit()
    {
        Session.getStaticInstance().reset();
        
        TextEditorApplication.getStage().close();
    }
    
    @FXML
    private void saveAllFiles()
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

    @FXML
    private void saveActualTab()
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

    @FXML
    private void saveActualFileAs()
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
    
    @FXML
    private void openFile()
    {
        File file = FileOperation.fileChooser().showOpenDialog(null);

        if (file == null)
        {
            return;
        }

        openFile(file);
    }
    
    private void openProyectDirectory(File file)
    {
        file = file.getAbsoluteFile();
        
        if (file.getPath().isEmpty()) 
        {
            return;
        }
        
        Session.getStaticInstance().proyectDirectory = file.getPath();

        treeView.setCellFactory(param -> new CustomTreeCell());
        treeView.setRoot(FileOperation.createTreeView(file));
    }
    
    private void openLastProjectDirectory()
    {
        File file = new File(Session.getStaticInstance().proyectDirectory);
        
        if (!file.exists() || !file.isDirectory())
        {
            return;
        }
        
        openProyectDirectory(file);
    }
    
    @FXML
    private void openNewProjectDirectory()
    {
        File file = FileOperation.directoryChooser().showDialog(null);
        
        if (file == null)
        {
            return;
        }
        
        openProyectDirectory(file);
    }

    @FXML
    private void newFile()
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
        
        StyleController.defaultStyle(formateableText, fileExtension);
        
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
        Stage stage = TextEditorApplication.getStage();

        // Add an event in which, when the window is shown, the last files are opened
        stage.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> openLastFiles());
        // Add an event in which, when the window is hidden, all files are saved
        stage.addEventHandler(WindowEvent.WINDOW_HIDING, event -> saveAllFiles());
        
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
        
        // Add an event in which, when the window is shown, the last project is opened
        stage.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> openLastProjectDirectory());
    }
}
