package com.lebastudios.sealcode.frontend;

import javafx.scene.control.Button;

public class IconButton extends Button
{
    private String iconName;
    
    public IconButton()
    {
        super();
        
        this.iconName = "";
    }
    
    public IconButton(String iconName)
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
