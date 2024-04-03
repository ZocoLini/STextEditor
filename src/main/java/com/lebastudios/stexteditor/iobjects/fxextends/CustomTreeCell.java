package com.lebastudios.stexteditor.iobjects.fxextends;

import com.lebastudios.stexteditor.iobjects.controllers.CustomTreeCellController;
import javafx.scene.control.TreeCell;
import javafx.scene.image.ImageView;

public class CustomTreeCell extends TreeCell<CustomTreeCellContent>
{
    private ImageView imageView;
    private final CustomTreeCellController controller;

    public CustomTreeCell() {
        imageView = new ImageView();
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        
        this.setOnMouseClicked(event -> 
        {
            if (getItem() != null) 
            {
                getItem().onMouseClicked(event);
            }
        });
        
        this.controller = new CustomTreeCellController(this);
    }
    
    @Override
    protected void updateItem(CustomTreeCellContent item, boolean empty) {
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
