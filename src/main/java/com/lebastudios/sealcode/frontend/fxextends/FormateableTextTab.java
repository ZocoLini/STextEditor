package com.lebastudios.sealcode.frontend.fxextends;

import com.lebastudios.sealcode.applogic.FileOperation;
import com.lebastudios.sealcode.applogic.config.Session;
import com.lebastudios.sealcode.applogic.txtformatter.StyleSetter;
import javafx.scene.control.Tab;

import java.io.File;

public final class FormateableTextTab extends Tab
{
    public FormateableTextTab(String name, String content, String fileExtension)
    {
        instanciador(name, content, fileExtension);
    }

    private void instanciador(String name, String content, String fileExtension)
    {
        this.setText(name);

        FormateableText formateableText = new FormateableText(content);

        this.setContent(formateableText);

        StyleSetter.defaultStyle(formateableText, fileExtension);

        this.setOnCloseRequest(event ->
                Session.getStaticInstance().filesOpen.remove(
                        this.getTabPane().getTabs().indexOf(
                                (Tab) event.getTarget()
                        )
                )
        );
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
    }

    public FormateableTextTab()
    {
        instanciador("new Text", "", "txt");
    }
}
