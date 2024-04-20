package com.lebastudios.sealcode.frontend.fxextends;

import com.lebastudios.sealcode.applogic.config.Session;
import com.lebastudios.sealcode.frontend.fxextends.treeviews.FileSystemTreeItem;
import javafx.scene.control.Tab;

public final class FormateableTextTab extends Tab
{
    public FormateableTextTab(String name, String content, FileSystemTreeItem treeItem)
    {
        this.setText(name);
        
        SealCodeArea sealCodeArea = new SealCodeArea(content, treeItem);

        this.setContent(sealCodeArea);
        
        addEventHandlers();
    }
    
    private void addEventHandlers()
    {
        this.setOnCloseRequest(event ->
                Session.getStaticInstance().filesOpen.remove(
                        this.getTabPane().getTabs().indexOf(
                                (Tab) event.getTarget()
                        )
                )
        );

    }
    
    public void saveFile()
    {
        ((SealCodeArea) this.getContent()).saveFile();
    }
}
