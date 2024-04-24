package com.lebastudios.sealcode.core.frontend.fxextends;

import javafx.scene.control.TreeView;

public class SettingsTreeView extends TreeView<String>
{
    public SettingsTreeView()
    {
        super();
        this.setCellFactory(param -> new SettingsTreeCell());
    }
}
