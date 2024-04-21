package com.lebastudios.sealcode.frontend.fxextends;

import com.lebastudios.sealcode.applogic.completations.Completation;
import javafx.scene.control.ListCell;

public class CompletationListCell extends ListCell<Completation>
{
    @Override
    protected void updateItem(Completation item, boolean empty)
    {
        super.updateItem(item, empty);
        
        if (empty || item == null)
        {
            setText(null);
            setGraphic(null);
        }
        else
        {
            setText(item.getValue());
            setGraphic(item.getIcon());
        }
    }
}
