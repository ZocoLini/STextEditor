package com.lebastudios.stexteditor.iobjects.managers;

import com.lebastudios.stexteditor.TextEditorApplication;
import com.lebastudios.stexteditor.applogic.config.Session;
import com.lebastudios.stexteditor.iobjects.fxextends.CustomTreeCellContent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;

public class MainManager extends Manager<BorderPane>
{
    private static MainManager instance;
    
    public static MainManager getInstance()
    {
        return instance;
    }

    public MainManager()
    {
        super(null);
        representingObject = mainPane;
        instance = this;
        
        instanciated = true;
    }

    /*          Objetos de la interfaz fijos          */
    /**************************************************/
    @FXML
    public TabPane tabPane;

    @FXML
    public TreeView<CustomTreeCellContent> treeView;
    
    @FXML
    private BorderPane mainPane;
    
    @FXML
    public MenuBar menuBar;
    
    @FXML
    public VBox leftVBox;
    
    @FXML
    public VBox rightVBox;
    
    @FXML
    public Button botonCompilar;
    @FXML
    public Button botonEjecutar;

    /*            Main Manager own methods            */
    /**************************************************/
    @FXML
    private void exit()
    {
        Session.getStaticInstance().reset();
        
        TextEditorApplication.getStage().close();
    }

    /*                Tab Pane Methods                */
    /**************************************************/
    
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

    /*                Tree View Methods               */
    /**************************************************/
    @FXML
    private void openNewProjectDirectory() {
        TreeViewManager.getInstance().openNewProjectDirectory();
    }

    /*                Left VBox Methods               */
    /**************************************************/
    @FXML
    private void compile()
    {
        RightVBoxManager.getInstance().compile();
    }
    
    @FXML
    private void execute()
    {
        RightVBoxManager.getInstance().execute();
    }
    
    /*                Override Methods                */
    /**************************************************/
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
