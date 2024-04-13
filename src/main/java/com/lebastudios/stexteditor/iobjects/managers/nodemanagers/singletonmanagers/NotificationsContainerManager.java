package com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers;

import com.lebastudios.stexteditor.iobjects.nodes.Notification;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class NotificationsContainerManager extends SingletonManager<BorderPane>
{
    private static NotificationsContainerManager instance;
    
    public static NotificationsContainerManager getInstance()
    {
        if (instance == null) instance = new NotificationsContainerManager();
        
        return instance;
    }
    
    private final VBox notiContainer;
    
    private NotificationsContainerManager() 
    {
        super(MainManager.getInstance().notifications);
        
        notiContainer = new VBox(5);
        notiContainer.setPadding(new Insets(20));
        
        managedObject.setCenter(new ScrollPane(notiContainer));
    }
    
    public void addNotification(Notification notification)
    {
        notiContainer.getChildren().add(notification);
    }
    
    @Override
    protected void loadChilds()
    {
        
    }
}
