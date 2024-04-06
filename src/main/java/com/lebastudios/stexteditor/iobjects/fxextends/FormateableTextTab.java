package com.lebastudios.stexteditor.iobjects.fxextends;

import com.lebastudios.stexteditor.applogic.FileOperation;
import com.lebastudios.stexteditor.applogic.config.global.Session;
import com.lebastudios.stexteditor.applogic.txtformatter.StyleSetter;
import com.lebastudios.stexteditor.iobjects.managers.objectmanagers.FormateableTextManager;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.MainManager;
import com.lebastudios.stexteditor.iobjects.nodes.FormateableText;
import javafx.scene.control.Tab;

import java.io.File;

public final class FormateableTextTab extends Tab
{
    private final FormateableTextManager controller;
    
    public FormateableTextTab(String name, String content, String fileExtension)
    {
        instanciador(name, content, fileExtension);

        controller = new FormateableTextManager(this);
    }

    public FormateableTextTab(File file)
    {
        String fileName = file.getName();

        try
        {
            String content = FileOperation.readFile(file);
            instanciador(fileName, content, FileOperation.getFileExtension(file));
        }
        catch (Exception e)
        {
            System.err.println("File not found, probably deleted");

            instanciador("new Text", "", "txt");
        }
        
        controller = new FormateableTextManager(this);
    }

    public FormateableTextTab()
    {
        instanciador("new Text", "", "txt");

        controller = new FormateableTextManager(this);
    }
    
    private void instanciador(String name, String content, String fileExtension)
    {
        this.setText(name);
        
        FormateableText formateableText = new FormateableText(content);

        this.setContent(formateableText);

        StyleSetter.defaultStyle(formateableText, fileExtension);

        this.setOnCloseRequest(event ->
                Session.getStaticInstance().filesOpen.remove(
                        MainManager.getInstance().codeTabPane.getTabs().indexOf(
                                (Tab) event.getTarget()
                        )
                )
        );
    }
}
