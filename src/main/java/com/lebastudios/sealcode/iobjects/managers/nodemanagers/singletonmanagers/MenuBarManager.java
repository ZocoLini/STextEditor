package com.lebastudios.sealcode.iobjects.managers.nodemanagers.singletonmanagers;

import com.lebastudios.sealcode.TextEditorApplication;
import com.lebastudios.sealcode.applogic.Resources;
import com.lebastudios.sealcode.applogic.config.Session;
import com.lebastudios.sealcode.iobjects.fxextends.Notification;
import com.lebastudios.sealcode.iobjects.managers.nodemanagers.singletonmanagers.tabpane.CodeTabPaneManager;
import com.lebastudios.sealcode.iobjects.managers.nodemanagers.singletonmanagers.treeview.ProyectTreeViewManager;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;

public class MenuBarManager extends SingletonManager<MenuBar>
{
    private static MenuBarManager instance;

    public static MenuBarManager getInstance()
    {
        if (instance == null) instance = new MenuBarManager();

        return instance;
    }

    private MenuBarManager()
    {
        super(MainManager.getInstance().menuBar);

        createMenuBar();
    }

    private void createMenuBar()
    {
        //**************************************************************************************//
        // Crear menú "File"
        Menu menuFile = new Menu("File");

        // Crear los elementos de menú y configurar sus acciones y gráficos
        MenuItem newFileMenuItem = new MenuItem("New File", getGraphic("newFile.png"));
        newFileMenuItem.setOnAction(e -> newFile());

        MenuItem openFileMenuItem = new MenuItem("Open File", getGraphic("openFile.png"));
        openFileMenuItem.setOnAction(e -> openFile());

        MenuItem saveFileMenuItem = new MenuItem("Save File", getGraphic("save.png"));
        saveFileMenuItem.setOnAction(e -> saveActualTab());

        MenuItem saveAsFileMenuItem = new MenuItem("Save File As", getGraphic("save.png"));
        saveAsFileMenuItem.setOnAction(e -> saveActualFileAs());

        SeparatorMenuItem separatorMenuItem1 = new SeparatorMenuItem();

        MenuItem openProjectMenuItem =
                new MenuItem("Open Project", getGraphic("openProyect.png"));
        openProjectMenuItem.setOnAction(e -> openNewProjectDirectory());

        SeparatorMenuItem separatorMenuItem2 = new SeparatorMenuItem();

        MenuItem settingsMenuItem = new MenuItem("Settings", getGraphic("settings.png"));
        settingsMenuItem.setOnAction(e -> openSettings());

        SeparatorMenuItem separatorMenuItem3 = new SeparatorMenuItem();

        MenuItem exitMenuItem = new MenuItem("Exit", getGraphic("exit.png"));
        exitMenuItem.setOnAction(e -> exit());

        // Agregar los elementos de menú al menú "File"
        menuFile.getItems().addAll(
                newFileMenuItem, openFileMenuItem, saveFileMenuItem, saveAsFileMenuItem,
                separatorMenuItem1, openProjectMenuItem, separatorMenuItem2,
                settingsMenuItem, separatorMenuItem3, exitMenuItem
        );

        //*********************************************************************//
        // Crear menú "Help"
        Menu menuHelp = new Menu("Help");

        // Crear el elemento de menú "About" y configurar su gráfico
        MenuItem aboutMenuItem = new MenuItem("About", getGraphic("help.png"));

        // Agregar el elemento de menú "About" al menú "Help"
        menuHelp.getItems().add(aboutMenuItem);

        // Agregar los menús al menú principal
        managedObject.getMenus().addAll(menuFile, menuHelp);
    }

    private static ImageView getGraphic(String image)
    {
        ImageView imageView = new ImageView(Resources.getIcon(image));
        
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        
        return imageView;
    }


    void saveActualTab()
    {
        CodeTabPaneManager.getInstance().saveActualTab();
    }
    
    void saveActualFileAs()
    {
        CodeTabPaneManager.getInstance().saveActualFileAs();
    }
    
    void openFile()
    {
        CodeTabPaneManager.getInstance().openFile();
    }
    
    void newFile()
    {
        CodeTabPaneManager.getInstance().newFile();
    }

    void exit()
    {
        Session.getStaticInstance().reset();

        TextEditorApplication.getStage().close();
    }

    void openNewProjectDirectory()
    {
        ProyectTreeViewManager.getInstance().openNewProjectDirectory();
    }

    void openSettings()
    {
        NotificationsContainerManager.getInstance().addNotification(new Notification("No existe la Stage settings."));
    }
    
    @Override
    protected void loadChilds()
    {

    }
}
