package com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.rightvbox;

import com.lebastudios.stexteditor.applogic.Resources;
import com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.MainSingletonManager;
import com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.SingletonManager;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class CompileButtonSingletonManager extends SingletonManager<Button>
{
    private static CompileButtonSingletonManager instance;

    public static CompileButtonSingletonManager getInstance()
    {
        if (instance == null) instance = new CompileButtonSingletonManager();
        
        return instance;
    }

    private CompileButtonSingletonManager()
    {
        super(MainSingletonManager.getInstance().botonCompilar);

        instanciated = true;
    }

    @Override
    protected void loadShelf()
    {
        ImageView compileIcon = new ImageView(Resources.getIcon("compile.png"));
        representingObject.setGraphic(compileIcon);
    }

    @Override
    public void loadChilds()
    {
        
    }
}
