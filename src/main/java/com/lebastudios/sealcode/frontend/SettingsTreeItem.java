package com.lebastudios.sealcode.frontend;

import com.lebastudios.sealcode.SealCodeApplication;
import javafx.fxml.FXMLLoader;

public class SettingsTreeItem extends IconTreeItem<String>
{
    private String fxmlResource = "notasignedscene.fxml";
    private Class<?> resoureceLoader = SealCodeApplication.class;
    
    public SettingsTreeItem()
    {
        super();

        this.addEventHandler(branchCollapsedEvent(), (event) ->
        {
            this.setExpanded(false);
        });
    }
    
    public FXMLLoader getFxmlLoader()
    {
        return new FXMLLoader(resoureceLoader.getResource(fxmlResource));
    }

    public Class<?> getResoureceLoader()
    {
        return resoureceLoader;
    }

    public void setResoureceLoader(Class<?> resoureceLoader)
    {
        this.resoureceLoader = resoureceLoader;
    }

    public String getFxmlResource()
    {
        return fxmlResource;
    }
    
    public void setFxmlResource(String fxmlResource)
    {
        this.fxmlResource = fxmlResource;
    }
}
