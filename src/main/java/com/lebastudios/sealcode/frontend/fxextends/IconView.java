package com.lebastudios.sealcode.frontend.fxextends;

import com.lebastudios.sealcode.applogic.Resources;
import com.lebastudios.sealcode.applogic.events.AppEvents;
import javafx.scene.image.ImageView;

public class IconView extends ImageView
{
    private static final int ICON_SIZE = 20;
    
    private String iconName;

    public IconView()
    {
        super();
        setSize();
    }

    public IconView(String iconName)
    {
        super(Resources.getIcon(iconName));
        setSize();
        
        this.iconName = iconName;
        AppEvents.onThemeChange.addListener(this::updateIconOnThemeChange);
    }

    private void setSize()
    {
        this.setFitHeight(ICON_SIZE);
        this.setFitWidth(ICON_SIZE);
    }
    
    private void updateIconOnThemeChange()
    {
        this.setImage(Resources.getIcon(iconName));
    }
}
