package com.lebastudios.sealcode.core.frontend.fxextends;

import com.lebastudios.sealcode.config.Session;
import javafx.scene.control.Tab;

public final class CodeTab extends Tab
{
    public CodeTab(String name, String content, FileSystemTreeItem treeItem)
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
