package com.lebastudios.sealcode.frontend.fxextends;

import com.lebastudios.sealcode.applogic.config.Session;
import com.lebastudios.sealcode.frontend.stages.MainStage;
import com.lebastudios.sealcode.controllers.MainStageController;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public final class MainMenuBar extends MenuBar
{
    public MainMenuBar()
    {
        super();

        createMenuBar();
    }

    private void createMenuBar()
    {
        //**************************************************************************************//
        // Crear menú "File"
        Menu menuFile = new Menu("File");

        // Crear los elementos de menú y configurar sus acciones y gráficos
        MenuItem newFileMenuItem = new MenuItem("New File", getGraphic("newFile.png"));
        newFileMenuItem.setOnAction(e -> MainStageController.getInstance().newFile());

        MenuItem openFileMenuItem = new MenuItem("Open File", getGraphic("openFile.png"));
        openFileMenuItem.setOnAction(e -> MainStageController.getInstance().openFile());

        MenuItem saveFileMenuItem = new MenuItem("Save File", getGraphic("save.png"));
        saveFileMenuItem.setOnAction(e -> MainStageController.getInstance().saveActualTab());

        MenuItem saveAsFileMenuItem = new MenuItem("Save File As", getGraphic("save.png"));
        saveAsFileMenuItem.setOnAction(e -> MainStageController.getInstance().saveActualFileAs());

        SeparatorMenuItem separatorMenuItem1 = new SeparatorMenuItem();

        MenuItem openProjectMenuItem =
                new MenuItem("Open Project", getGraphic("openProyect.png"));
        openProjectMenuItem.setOnAction(e -> MainStageController.getInstance().openNewProjectDirectory());

        SeparatorMenuItem separatorMenuItem2 = new SeparatorMenuItem();

        MenuItem settingsMenuItem = new MenuItem("Settings", getGraphic("settings.png"));
        settingsMenuItem.setOnAction(e -> MainStageController.getInstance().openSettings());

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
        this.getMenus().addAll(menuFile, menuHelp);
    }

    private static IconView getGraphic(String image)
    {
        return new IconView(image);
    }

    void exit()
    {
        Session.getStaticInstance().reset();

        MainStage.getInstance().close();
    }
}
