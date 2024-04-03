package com.lebastudios.stexteditor.iobjects.managers;

import com.lebastudios.stexteditor.TextEditorApplication;
import com.lebastudios.stexteditor.applogic.config.Session;
import com.lebastudios.stexteditor.iobjects.controllers.Controller;
import com.lebastudios.stexteditor.iobjects.fxextends.CustomTreeCellContent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.stage.WindowEvent;

public class MainManager extends Manager<Parent>
{
    private static MainManager instance;
    
    public static MainManager getInstance()
    {
        return instance;
    }

    public MainManager()
    {
        super(null);
        instance = this;

        instanciated = true;
    }

    @FXML
    public TabPane tabPane;

    @FXML
    public TreeView<CustomTreeCellContent> treeView;
    
    @FXML
    private void exit()
    {
        Session.getStaticInstance().reset();

        TextEditorApplication.getStage().close();
    }

    @FXML
    private void saveActualTab() {
        TabPaneManager.getInstance().saveActualTab();
    }

    @FXML
    private void saveActualFileAs() {
        TabPaneManager.getInstance().saveActualFileAs();
    }

    @FXML
    private void openFile() {
        TabPaneManager.getInstance().openFile();
    }
    
    @FXML
    private void newFile() {
        TabPaneManager.getInstance().newFile();
    }
    
    @FXML
    private void openNewProjectDirectory() {
        TreeViewManager.getInstance().openNewProjectDirectory();
    }

    @Override
    protected void addEventHandlers()
    {
        // Add an event in which, when the window is shown, the last files are opened
        stage.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> 
                TabPaneManager.getInstance().openLastFiles());
        // Add an event in which, when the window is hidden, all files are saved
        stage.addEventHandler(WindowEvent.WINDOW_HIDING, event -> 
                TabPaneManager.getInstance().saveAllFiles());
        
        stage.addEventHandler(WindowEvent.WINDOW_SHOWN,
                event -> TreeViewManager.getInstance().openLastProjectDirectory());
    }
}
