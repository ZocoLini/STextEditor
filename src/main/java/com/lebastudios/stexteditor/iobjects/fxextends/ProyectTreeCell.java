package com.lebastudios.stexteditor.iobjects.fxextends;

import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.instanciablemanager.ProyectTreeCellManager;
import javafx.scene.control.TreeCell;
import javafx.scene.image.ImageView;

public class ProyectTreeCell extends TreeCell<ProyectTreeCellContent>
{
    private final ImageView imageView;

    public ProyectTreeCell() {
        imageView = new ImageView();
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        
        new ProyectTreeCellManager(this);
    }
    
    @Override
    protected void updateItem(ProyectTreeCellContent item, boolean empty) 
    {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item.getName());
            imageView.setImage(item.getImage());
            setGraphic(imageView);
        }
    }
}
