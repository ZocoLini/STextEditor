package com.lebastudios.sealcode.frontend;

import javafx.scene.control.TreeView;

public class SettingsTreeView extends TreeView<String>
{
    public SettingsTreeView()
    {
        super();
        this.setCellFactory(param -> new SettingsTreeCell());
    }
}
