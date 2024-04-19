package com.lebastudios.sealcode.frontend.fxextends.treeviews;

import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

public class SettingsTreeItem extends TreeItem<String>
{
    private final String fxmlName;
    
    public SettingsTreeItem(String name, String fxmlName, ImageView icon)
    {
        super(name, icon);
        
        this.fxmlName = fxmlName;
        
        addEventsHandlers();
    }

    public SettingsTreeItem(String name, String fxmlName)
    {
        this(name, fxmlName, null);
    }
    
    private void addEventsHandlers()
    {
        this.addEventHandler(TreeItem.branchCollapsedEvent(), (event) ->
        {
            this.setExpanded(false);
        });
    }
    
    public String getFxmlName()
    {
        return fxmlName;
    }
}
