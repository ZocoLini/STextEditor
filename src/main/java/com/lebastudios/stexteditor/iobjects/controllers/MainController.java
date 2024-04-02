package com.lebastudios.stexteditor.iobjects.controllers;

import com.lebastudios.stexteditor.TextEditorApplication;
import com.lebastudios.stexteditor.applogic.config.Session;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.stage.WindowEvent;

public class MainController extends Controller
{
    private static MainController instance;
    
    public static MainController getInstance()
    {
        return instance;
    }

    public MainController()
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
    private void saveActualTab() {
        TabPaneController.getInstance().saveActualTab();
    }

    @FXML
    private void saveActualFileAs() {
        TabPaneController.getInstance().saveActualFileAs();
    }

    @FXML
    private void openFile() {
        TabPaneController.getInstance().openFile();
    }
    
    @FXML
    private void newFile() {
        TabPaneController.getInstance().newFile();
    }
    
    @FXML
    private void openNewProjectDirectory() {
        TreeViewController.getInstance().openNewProjectDirectory();
    }

    @Override
    protected void addEventHandlers()
    {
        // Add an event in which, when the window is shown, the last files are opened
        stage.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> 
                TabPaneController.getInstance().openLastFiles());
        // Add an event in which, when the window is hidden, all files are saved
        stage.addEventHandler(WindowEvent.WINDOW_HIDING, event -> 
                TabPaneController.getInstance().saveAllFiles());
        
        stage.addEventHandler(WindowEvent.WINDOW_SHOWN,
                event -> TreeViewController.getInstance().openLastProjectDirectory());
    }
}
