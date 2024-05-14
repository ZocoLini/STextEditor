package com.lebastudios.sealcode.core.frontend.fxextends;

import com.lebastudios.sealcode.core.controllers.SettingsStageController;
import javafx.scene.control.TreeCell;

public class SettingsTreeCell extends TreeCell<String>
{
    public SettingsTreeCell()
    {
        super();

        this.setOnMouseClicked(event ->
        {
            if (this.getTreeItem() == null) return;
            if (this.getTreeItem().getClass() != SettingsTreeItem.class) return;

            SettingsStageController.getInstance().loadNewSettingsPane(((SettingsTreeItem) this.getTreeItem()).getFxmlSceneName());
        });
    }
    
    @Override
    protected void updateItem(String item, boolean empty)
    {
        super.updateItem(item, empty);

        if (empty || item == null)
        {
            setText(null);
            setGraphic(null);
        }
        else
        {
            setText(item);
            this.setGraphic(this.getTreeItem().getGraphic());
        }
    }
}
