package com.lebastudios.stexteditor.interfacecontrollers.proyecttreeview;

import javafx.scene.control.TreeCell;
import javafx.scene.image.ImageView;
public class CustomTreeCell extends TreeCell<TreeObjectController>
{
    private ImageView imageView;

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
    }
    
    @Override
    protected void updateItem(TreeObjectController item, boolean empty) {
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
