package com.lebastudios.sealcode.core.frontend.fxextends;

import com.lebastudios.sealcode.config.Resources;
import com.lebastudios.sealcode.core.logic.completations.CompletationsPopup;
import com.lebastudios.sealcode.events.AppEvents;
import com.lebastudios.sealcode.util.DocumentsOperations;
import com.lebastudios.sealcode.util.FileOperation;
import com.lebastudios.sealcode.util.TextModInf;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyledDocument;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public final class SealCodeArea extends CodeArea
{
    private final FileSystemTreeItem fileSystemTreeItem;
    public final String fileExtension;

    private boolean modified = false;
    private boolean instantiated = false;

    public SealCodeArea(String string, FileSystemTreeItem fileSystemTreeItem)
    {
        super(string);

        fileExtension = FileOperation.getFileExtension(fileSystemTreeItem.getRepresentingFile());

        this.fileSystemTreeItem = fileSystemTreeItem;

        this.addEventHandlers();

        this.setParagraphGraphicFactory(LineNumberFactory.get(this));

        updateResources();

        new CompletationsPopup(this);
        AppEvents.onSealCodeAreaCreated.invoke(this);

        instantiated = true;
    }

    private void updateResources()
    {
        final String fileExtension = FileOperation.getFileExtension(fileSystemTreeItem.getRepresentingFile());
        
        String dontDelete = this.getStylesheets().get(0);
        String dontDelete2 = this.getStylesheets().get(1);
        
        this.getStylesheets().clear();
        
        this.getStylesheets().add(dontDelete);
        this.getStylesheets().add(dontDelete2);
        
        this.getStylesheets().add(Resources.getLangCommonStyle());
        this.getStylesheets().add(Resources.getExtensionStyle(fileExtension));
    }

    private void addEventHandlers()
    {
        // Cambia el estilo de resaltado al cambaiar de tema
        AppEvents.onThemeChange.addListener(this::updateResources);

        // Añade un evento en el que, si se pusa Ctrl + Z, se deshace la última acción
        this.addEventHandler(KeyEvent.KEY_PRESSED, event ->
        {
            if (event.isControlDown()
                    && event.getCode().equals(javafx.scene.input.KeyCode.Z)
                    && !event.isShiftDown()
                    && this.isUndoAvailable())
            {
                this.undo();
            }
        });

        // Añade un evento en el que, si se pusa Ctrl + Y, se rehace la última acción
        this.addEventHandler(KeyEvent.KEY_PRESSED, event ->
        {
            if (event.isControlDown()
                    && event.getCode().equals(javafx.scene.input.KeyCode.Y)
                    && !event.isShiftDown()
                    && this.isRedoAvailable())
            {
                this.redo();
            }
        });
    }

    public void saveFile()
    {
        if (!this.modified) return;

        new Thread(() ->
        {
            try (FileWriter fileWriter = new FileWriter(fileSystemTreeItem.getRepresentingFile()))
            {
                fileWriter.write(this.getText());
                System.out.println("Archivo guardado:" + fileSystemTreeItem.getRepresentingFile());
                modified = false;
            } catch (IOException exception)
            {
                System.err.println("Error al guardar el archivo: " + exception.getMessage());
            }
        }).start();
    }

    public String getPreviusChar(int position)
    {
        if (position == 0) return "";
        return this.getText(position - 1, position);
    }

    public String getNoBlankPreviusChar(int position)
    {
        if (position == 0) return "";

        String previusChar = this.getText(position - 1, position);

        if (previusChar.equals(" ")) return getNoBlankPreviusChar(position - 1);

        return previusChar;
    }

    public String getNextChar(int position)
    {
        if (position >= this.getText().length()) return "";

        return this.getText(position, position + 1);
    }

    public String getNoBlankNextChar(int position)
    {
        if (position >= this.getText().length() - 1) return "";

        String nextChar = this.getText(position, position + 1);

        if (nextChar.equals(" ")) return getNoBlankNextChar(position + 1);

        return nextChar;
    }

    public int getParagraphIndentation(int paragraph)
    {
        final var firstParagraphText = this.getParagraph(paragraph).getText();
        int i;

        for (i = 0; i < firstParagraphText.length(); i++)
        {
            if (firstParagraphText.charAt(i) != ' ') return i;
        }

        return i;
    }

    public int getParagraphIndentation()
    {
        return getParagraphIndentation(getCurrentParagraph());
    }
    
    public int paragraphStart(int paragraph)
    {
        int start = 0;

        for (int i = 0; i < paragraph; i++)
        {
            start += this.getParagraph(i).length() + 1;
        }

        return start;
    }

    public int paragraphEnd(int paragraph)
    {
        return paragraphStart(paragraph) + this.getParagraph(paragraph).length();
    }

    @Override
    public void replace(int start, int end, StyledDocument<Collection<String>, String, Collection<String>> replacement)
    {
        modified = true;

        final String newText = replacement.getText();
        final String oldText = start == end ? "" : this.getText(start, end);

        TextModInf modInf = new TextModInf(start, end, newText);

        AppEvents.onTextModifiedBefore.invoke(fileExtension, oldText, modInf, this);
        AppEvents.onTextModifiedBefore.invoke("All", oldText, modInf, this);

        if (!instantiated)
        {
            super.replace(start, end,
                    DocumentsOperations.createStyledDocument(newText));
            return;
        }
        
        if (!newText.isEmpty() && !oldText.isEmpty())
        {
            // Remplazo
            AppEvents.onTextReplaced.invoke(fileExtension, oldText, modInf, this);
            AppEvents.onTextReplaced.invoke("All", oldText, modInf, this);
        } else if (newText.isEmpty())
        {
            // Eliminación
            AppEvents.onTextDeleted.invoke(fileExtension, oldText, modInf, this);
            AppEvents.onTextDeleted.invoke("All", oldText, modInf, this);
        } else if (oldText.isEmpty())
        {
            if (!instantiated) return;

            // Adicion
            AppEvents.onTextInserted.invoke(fileExtension, oldText, modInf, this);
            AppEvents.onTextInserted.invoke("All", oldText, modInf, this);
        }

        // Ejecutar siempre
        AppEvents.onTextModifiedAfter.invoke(fileExtension, oldText, modInf, this);
        AppEvents.onTextModifiedAfter.invoke("All", oldText, modInf, this);

        // Ver donde tiene que posicionar el caret al final
        if (modInf.textModificated.contains("$END$"))
        {
            int caretDesiredPosition = modInf.textModificated.indexOf("$END$");
            modInf.textModificated = modInf.textModificated.replace("$END$", "");
            modInf.caretPos = caretDesiredPosition + start;
        }

        /* Añadimos extra carets pendiente de terminar
        Matcher matcher = Pattern.compile("\\$VAR\\$").matcher(modInf.textModificated);
        List<Integer> posicionesCaretVAR = new ArrayList<>();
        
        while (matcher.find())
        {
            posicionesCaretVAR.add(matcher.start());
            System.out.println(matcher.start());
        }
        
        if (!posicionesCaretVAR.isEmpty()) 
        {
            this.addCaret(new CaretNode("caret1", this, start + posicionesCaretVAR.get(0)));
        }
         */

        super.replace(modInf.start, modInf.end,
                DocumentsOperations.createStyledDocument(modInf.textModificated));

        if (modInf.caretPos != -1)
        {
            this.moveTo(modInf.caretPos);
        }
    }
}
