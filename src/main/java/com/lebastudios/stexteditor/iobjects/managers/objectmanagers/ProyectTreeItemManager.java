package com.lebastudios.stexteditor.iobjects.managers.objectmanagers;

import com.lebastudios.stexteditor.iobjects.fxextends.ProyectTreeCellContent;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.treeview.ProyectTreeViewManager;
import javafx.scene.control.TreeItem;

import java.io.File;

public class ProyectTreeItemManager extends ObjectManager<TreeItem<ProyectTreeCellContent>>
{
    private boolean monitorear = true;
    
    public ProyectTreeItemManager(TreeItem<ProyectTreeCellContent> managedObject)
    {
        super(managedObject);
        
        new Thread(() -> 
        {
            try
            {
                monitorearHijos();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }).start();
    }
    
    private void monitorearHijos() throws InterruptedException
    {
        // TODO: Monitorea correctamente pero lanza una excepciń al borrar un directorio
        // TODO: Queda el monitoreo de ciertas carpetas aunque estas se eliminen
        
        if (!managedObject.getValue().getRepresentingFile().isDirectory()) return;
        
        while (monitorear)
        {
            Thread.sleep(1000 / 15);
            
            if (managedObject.getChildren().size() != managedObject.getValue().getRepresentingFile().listFiles().length)
            {
                managedObject.getChildren().clear();
                
                for (File file : managedObject.getValue().getRepresentingFile().listFiles())
                {
                    managedObject.getChildren().add(ProyectTreeViewManager.createTreeView(file));
                }
            }
        }
    }
}
