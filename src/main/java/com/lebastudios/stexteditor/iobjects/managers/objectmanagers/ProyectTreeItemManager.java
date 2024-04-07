package com.lebastudios.stexteditor.iobjects.managers.objectmanagers;

import com.lebastudios.stexteditor.iobjects.fxextends.ProyectTreeCellContent;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.treeview.ProyectTreeViewManager;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.util.List;

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
        final var representingFile = managedObject.getValue().getRepresentingFile();
        
        while (monitorear)
        {
            Thread.sleep(5000);
            
            if (!representingFile.exists()) 
            {
                System.out.println("Se ha eliminado " + representingFile);
                
                managedObject.getParent().getChildren().remove(managedObject);
                
                monitorear = false;
                continue;
            }

            if (!representingFile.isDirectory()) continue;
            
            final var children = managedObject.getChildren();
            
            if (children.size() == representingFile.listFiles().length) continue;

            for (File file : representingFile.listFiles())
            {
                if (!isRepresented(file))
                {
                    System.out.println("Se ha añadido " + file);
                    children.add(ProyectTreeViewManager.createTreeView(file));
                }
            }
        }
    }
    
    private boolean isRepresented(File file)
    {
        for (var variable : managedObject.getChildren())
        {
            if (variable.getValue().getRepresentingFile().equals(file)) 
            {
                return true;
            }
        }
        
        return false;
    }
}
