package com.lebastudios.sealcode.core.frontend.fxextends;

import javafx.scene.control.TreeItem;

public class SettingsTreeItem extends IconTreeItem<String>
{
    private String fxmlSceneName = "notasignedscene.fxml";
    
    public SettingsTreeItem()
    {
        super();

        this.addEventHandler(TreeItem.branchCollapsedEvent(), (event) ->
        {
            this.setExpanded(false);
        });
    }
    
    public String getFxmlSceneName()
    {
        return fxmlSceneName;
    }
    
    public void setFxmlSceneName(String fxmlSceneName)
    {
        this.fxmlSceneName = fxmlSceneName;
    }
}
