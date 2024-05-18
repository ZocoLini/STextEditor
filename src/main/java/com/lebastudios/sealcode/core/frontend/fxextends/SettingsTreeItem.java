package com.lebastudios.sealcode.core.frontend.fxextends;

import javafx.scene.control.TreeItem;

public class SettingsTreeItem extends IconTreeItem<String>
{
    private String fxmlResource = "notasignedscene.fxml";
    
    public SettingsTreeItem()
    {
        super();

        this.addEventHandler(TreeItem.branchCollapsedEvent(), (event) ->
        {
            this.setExpanded(false);
        });
    }
    
    public String getFxmlResource()
    {
        return fxmlResource;
    }
    
    public void setFxmlResource(String fxmlResource)
    {
        this.fxmlResource = fxmlResource;
    }
}
