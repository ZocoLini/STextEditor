package com.lebastudios.sealcode.iobjects.managers.nodemanagers.singletonmanagers;

import com.lebastudios.sealcode.iobjects.fxextends.ProyectTreeCellContent;
import com.lebastudios.sealcode.iobjects.managers.nodemanagers.singletonmanagers.leftvbox.LeftVBoxManager;
import com.lebastudios.sealcode.iobjects.managers.nodemanagers.singletonmanagers.tabpane.CodeTabPaneManager;
import com.lebastudios.sealcode.iobjects.managers.nodemanagers.singletonmanagers.treeview.ProyectTreeViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;

public class MainManager extends SingletonManager<BorderPane>
{
    private static MainManager instance;
    public BorderPane notifications;

    public static MainManager getInstance()
    {
        return instance;
    }

    public MainManager()
    {
        super(null);
        managedObject = mainPane;
        instance = this;
    }

    /*          Objetos de la interfaz fijos          */
    /**************************************************/
    @FXML
    public TabPane codeTabPane;
    @FXML
    public TreeView<ProyectTreeCellContent> proyectFileTreeView;
    @FXML
    private BorderPane mainPane;
    @FXML
    public MenuBar menuBar;
    @FXML
    public VBox leftVBox;
    @FXML
    public Button botonCompilar;
    @FXML
    public Button botonEjecutar;
    @FXML
    public Button botonTerminal;
    @FXML
    public Button botonProyectFileSystem;
    @FXML
    public TextArea consoleTextArea;
    @FXML
    public SplitPane codeTabPaneContainer;
    @FXML
    public SplitPane terminalContainer;
    @FXML
    public Button botonNotificaciones;

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
        MenuBarManager.getInstance().load();
        CodeTabPaneManager.getInstance().load();
        ProyectTreeViewManager.getInstance().load();
        LeftVBoxManager.getInstance().load();
        NotificationsContainerManager.getInstance().load();
        ConsoleTextAreaManager.getInstance().load();
    }
}
