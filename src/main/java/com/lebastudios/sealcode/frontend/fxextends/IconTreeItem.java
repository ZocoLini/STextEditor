package com.lebastudios.sealcode.frontend.fxextends;

import javafx.scene.control.TreeItem;

public class IconTreeItem<T> extends TreeItem<T>
{
    private String iconName;

    public IconTreeItem(T value, String iconPath)
    {
        super(value);
        this.iconName = iconPath;
        
        updateIcon();
    }

    public String getIconName()
    {
        return iconName;
    }
    
    public void setIconName(String iconName)
    {
        this.iconName = iconName;
        updateIcon();
    }
    
    private void updateIcon()
    {
        this.setGraphic(new IconView(iconName));
    }
}
