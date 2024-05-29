package com.lebastudios.sealcode.frontend;

import javafx.scene.control.MenuItem;

public class IconMenuItem extends MenuItem
{
    private String iconName;
    
    public IconMenuItem()
    {
        super();
        
        this.iconName = "";
    }
    
    public IconMenuItem(String iconName)
    {
        super();
        
        this.iconName = iconName;

        actualizarIcono();
    }
    
    public String getIconName() {
        return iconName;
    }
    
    public void setIconName(String iconName) {
        this.iconName = iconName;
        actualizarIcono();
    }
    
    private void actualizarIcono() {
        IconView iconView = new IconView(iconName);
        this.setGraphic(iconView);
    }
}
