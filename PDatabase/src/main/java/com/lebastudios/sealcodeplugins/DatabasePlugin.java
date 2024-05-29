package com.lebastudios.sealcodeplugins;

import com.lebastudios.sealcode.events.AppEvents;
import com.lebastudios.sealcode.IPlugin;
import com.lebastudios.sealcode.frontend.IconTreeItem;
import com.lebastudios.sealcodeplugins.controllers.settingsPanels.AccountController;
import com.lebastudios.sealcodeplugins.database.MainDBManager;
import com.lebastudios.sealcodeplugins.database.MongoDBManager;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class DatabasePlugin implements IPlugin
{
    @Override
    public void initialize()
    {
        System.out.println("Starting Database Plugin");
        
        if (MainDBManager.getInstance().isAnyAccountConnected())
        {
            MongoDBManager.getInstance().pullUserFiles();
        }

        AppEvents.onAppExit.addListener(() -> new Thread(() -> {
                    if (MainDBManager.getInstance().isAnyAccountConnected())
                    {
                        MongoDBManager.getInstance().pushUserFiles();
                    }
                }).start()
        );

        AppEvents.onLoadedSettingsTreeView.addListener(treeView ->
        {
            try
            {
                IconTreeItem<String> accountMenu = new FXMLLoader(DatabasePlugin.class.getResource(
                        "settingsAdittion.fxml")
                ).load();
                
                treeView.getRoot().getChildren().add(accountMenu);
                treeView.getSelectionModel().selectedItemProperty().addListener((a, b, c) ->
                {
                    if (!c.equals(accountMenu.getChildren().get(0))) return;

                    AccountController.loadPane();
                });
            } catch (IOException e)
            {
                System.err.println("Error while configuring custom settings tree view.");
                e.printStackTrace();
            }
        });
    }
}