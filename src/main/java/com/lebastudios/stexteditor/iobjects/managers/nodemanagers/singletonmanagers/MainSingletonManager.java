package com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers;

import com.lebastudios.stexteditor.TextEditorApplication;
import com.lebastudios.stexteditor.applogic.config.global.Session;
import com.lebastudios.stexteditor.iobjects.fxextends.ProyectFileTreeCellContent;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.leftvbox.LeftVBoxManager;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.rightvbox.CompileButtonManager;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.rightvbox.ExecuteButtonManager;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.rightvbox.RightVBoxManager;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.tabpane.CodeTabPaneManager;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.treeview.ProyectFilesTreeViewManager;
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
        managedObject = mainPane;
        instance = this;
        
        instanciated = true;
    }

    /*          Objetos de la interfaz fijos          */
    /**************************************************/
    @FXML
    public TabPane codeTabPane;
    @FXML
    public TreeView<ProyectFileTreeCellContent> proyectFileTreeView;
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
        CodeTabPaneManager.getInstance().saveActualTab();
    }

    @FXML
    private void saveActualFileAs() {
        CodeTabPaneManager.getInstance().saveActualFileAs();
    }

    @FXML
    private void openFile() {
        CodeTabPaneManager.getInstance().openFile();
    }
    
    @FXML
    private void newFile() {
        CodeTabPaneManager.getInstance().newFile();
    }

    /*                Tree View Methods               */
    /**************************************************/
    @FXML
    private void openNewProjectDirectory() {
        ProyectFilesTreeViewManager.getInstance().openNewProjectDirectory();
    }

    /*                Right VBox Methods               */
    /**************************************************/
    @FXML
    private void compile()
    {
        CompileButtonManager.getInstance().onAction();
    }
    
    @FXML
    private void execute()
    {
        ExecuteButtonManager.getInstance().onAction();
    }
    
    /*                Override Methods                */
    /**************************************************/
    @Override
    protected void addEventHandlers()
    {
        // Add an event in which, when the window is hidden, all files are saved
        stage.addEventHandler(WindowEvent.WINDOW_HIDING, event -> 
                CodeTabPaneManager.getInstance().saveAllFiles());
    }

    @Override
    public void loadChilds()
    {
        CodeTabPaneManager.getInstance().load();
        ProyectFilesTreeViewManager.getInstance().load();
        LeftVBoxManager.getInstance().load();
        RightVBoxManager.getInstance().load();
    }
}
