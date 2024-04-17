package com.lebastudios.sealcode.frontend.fxextends;

import com.lebastudios.sealcode.applogic.Resources;
import javafx.scene.image.ImageView;

public class IconView extends ImageView
{
    private static final int ICON_SIZE = 20;

    public IconView()
    {
        super();
        setSize();
    }

    public IconView(String iconName)
    {
        super(Resources.getIcon(iconName));
        setSize();
    }

    private void setSize()
    {
        this.setFitHeight(ICON_SIZE);
        this.setFitWidth(ICON_SIZE);
    }
}
