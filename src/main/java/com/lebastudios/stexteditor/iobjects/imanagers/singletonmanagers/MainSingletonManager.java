package com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers;

import com.lebastudios.stexteditor.TextEditorApplication;
import com.lebastudios.stexteditor.applogic.config.Session;
import com.lebastudios.stexteditor.iobjects.fxextends.CustomTreeCellContent;
import com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.leftvbox.LeftVBoxSingletonManager;
import com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.rightvbox.RightVBoxSingletonManager;
import com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.tabpane.TabPaneSingletonManager;
import com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.treeview.TreeViewSingletonManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;

public class MainSingletonManager extends SingletonManager<BorderPane>
{
    private static MainSingletonManager instance;
    
    public static MainSingletonManager getInstance()
    {
        return instance;
    }

    public MainSingletonManager()
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
    @FXML
    public Button botonTerminal;

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
        TabPaneSingletonManager.getInstance().saveActualTab();
    }

    @FXML
    private void saveActualFileAs() {
        TabPaneSingletonManager.getInstance().saveActualFileAs();
    }

    @FXML
    private void openFile() {
        TabPaneSingletonManager.getInstance().openFile();
    }
    
    @FXML
    private void newFile() {
        TabPaneSingletonManager.getInstance().newFile();
    }

    /*                Tree View Methods               */
    /**************************************************/
    @FXML
    private void openNewProjectDirectory() {
        TreeViewSingletonManager.getInstance().openNewProjectDirectory();
    }

    /*                Left VBox Methods               */
    /**************************************************/
    @FXML
    private void compile()
    {
        RightVBoxSingletonManager.getInstance().compile();
    }
    
    @FXML
    private void execute()
    {
        RightVBoxSingletonManager.getInstance().execute();
    }
    
    /*                Override Methods                */
    /**************************************************/
    @Override
    protected void addEventHandlers()
    {
        // Add an event in which, when the window is shown, the last files are opened
        stage.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> 
                TabPaneSingletonManager.getInstance().openLastFiles());
        // Add an event in which, when the window is hidden, all files are saved
        stage.addEventHandler(WindowEvent.WINDOW_HIDING, event -> 
                TabPaneSingletonManager.getInstance().saveAllFiles());
        
        stage.addEventHandler(WindowEvent.WINDOW_SHOWN,
                event -> TreeViewSingletonManager.getInstance().openLastProjectDirectory());
    }

    @Override
    public void loadChilds()
    {
        TabPaneSingletonManager.getInstance().load();
        TreeViewSingletonManager.getInstance().load();
        LeftVBoxSingletonManager.getInstance().load();
        RightVBoxSingletonManager.getInstance().load();
    }
}
