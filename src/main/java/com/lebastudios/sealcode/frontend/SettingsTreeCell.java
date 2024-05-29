package com.lebastudios.sealcode.frontend;

import com.lebastudios.sealcode.controllers.SettingsStageController;
import com.lebastudios.sealcode.controllers.SettingsPaneController;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;

import java.io.IOException;

public class SettingsTreeCell extends TreeCell<String>
{
    public SettingsTreeCell()
    {
        super();

        this.setOnMouseClicked(event ->
        {
            if (this.getTreeItem() == null) return;
            if (this.getTreeItem().getClass() != SettingsTreeItem.class) return;

            final var fxmlLoader = ((SettingsTreeItem) this.getTreeItem()).getFxmlLoader();

            SettingsStageController.getInstance().loadNewSettingsPane(fxmlLoader);
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
