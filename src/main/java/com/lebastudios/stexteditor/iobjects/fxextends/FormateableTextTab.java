package com.lebastudios.stexteditor.iobjects.fxextends;

import com.lebastudios.stexteditor.applogic.FileOperation;
import com.lebastudios.stexteditor.applogic.config.Session;
import com.lebastudios.stexteditor.applogic.txtformatter.StyleSetter;
import com.lebastudios.stexteditor.iobjects.controllers.FormateableTextController;
import com.lebastudios.stexteditor.iobjects.controllers.MainController;
import com.lebastudios.stexteditor.iobjects.nodes.FormateableText;
import javafx.scene.control.Tab;

import java.io.File;

public final class FormateableTextTab extends Tab
{
    private final FormateableTextController controller;
    
    public FormateableTextTab(String name, String content, String fileExtension)
    {
        instanciador(name, content, fileExtension);

        controller = new FormateableTextController(this);
    }

    public FormateableTextTab(File file)
    {
        String fileName = file.getName();
        String content;

        try
        {
            content = FileOperation.read(file);
        }
        catch (Exception e)
        {
            System.err.println("File not found, probably deleted");
            content = "";
        }
       
        instanciador(fileName, content, FileOperation.getFileExtension(file));

        controller = new FormateableTextController(this);
    }

    public FormateableTextTab()
    {
        instanciador("new Text", "", "txt");

        controller = new FormateableTextController(this);
    }
    
    private void instanciador(String name, String content, String fileExtension)
    {
        this.setText(name);
        
        FormateableText formateableText = new FormateableText(content);

        this.setContent(formateableText);

        StyleSetter.defaultStyle(formateableText, fileExtension);

        this.setOnCloseRequest(event ->
                Session.getStaticInstance().filesOpen.remove(
                        MainController.getInstance().tabPane.getTabs().indexOf(
                                (Tab) event.getTarget()
                        )
                )
        );
    }
}
