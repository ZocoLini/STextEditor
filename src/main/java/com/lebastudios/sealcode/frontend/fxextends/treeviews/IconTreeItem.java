package com.lebastudios.sealcode.frontend.fxextends.treeviews;

import com.lebastudios.sealcode.frontend.fxextends.IconView;
import javafx.scene.control.TreeItem;

public class IconTreeItem<T> extends TreeItem<T>
{
    private String iconName;

    public IconTreeItem() {}
    
    public IconTreeItem(T value, String iconName)
    {
        super(value);
        this.iconName = iconName;
        
        actualizarIcono();
    }

    public String getIconName()
    {
        return iconName;
    }
    
    public void setIconName(String iconName)
    {
        this.iconName = iconName;
        actualizarIcono();
    }
    
    private void actualizarIcono()
    {
        this.setGraphic(new IconView(iconName));
    }
}
