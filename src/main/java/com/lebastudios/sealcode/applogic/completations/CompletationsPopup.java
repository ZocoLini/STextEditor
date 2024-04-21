package com.lebastudios.sealcode.applogic.completations;

import com.lebastudios.sealcode.events.AppEvents;
import com.lebastudios.sealcode.frontend.fxextends.CompletationListCell;
import com.lebastudios.sealcode.frontend.fxextends.SealCodeArea;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Popup;

import java.util.TreeSet;


public class CompletationsPopup implements ChangeListener<String>
{
    private TreeSet<Completation> completations;
    
    private final ListView<Completation> completationSListView = new ListView<>();
    private final Popup completationsBox = new Popup();
    private final SealCodeArea sealCodeArea;
    
    private String currentWord = "";
    
    public CompletationsPopup(SealCodeArea sealCodeArea)
    {
        this.sealCodeArea = sealCodeArea;

        loadCompletations();
        
        completationsBox.getContent().add(completationSListView);
        completationSListView.setMaxHeight(80);
        
        completationSListView.setCellFactory(param -> new CompletationListCell());
        
        AppEvents.onProfileChange.addListener(this::loadCompletations);
        
        sealCodeArea.textProperty().addListener(this);
        sealCodeArea.addEventHandler(KeyEvent.ANY, event ->
        {
            switch (event.getCode())
            {
                case ESCAPE -> reset();
                case ENTER -> complete();
            }
        });
    }

    private void loadCompletations()
    {
        completations = CompletationsLoader.getAllCompletations(sealCodeArea.fileExtension);
    }
    
    private void reset()
    {
        completationSListView.getItems().clear();
        completationsBox.hide();
    }
    
    private void complete()
    {
        final var caretPosition = sealCodeArea.getCaretPosition();
        
        final var completation = completationSListView.getSelectionModel().getSelectedItem();
        
        if (completation == null) return;
        
        final var text = completation.getCompletation();
        
        sealCodeArea.replaceText(caretPosition - currentWord.length(), caretPosition, text);
        
        reset();
        
        sealCodeArea.requestFocus();
    }
    
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
    {
        // TODO: Posible problema con el rendimiento en este método
        
        currentWord = readLastWord();

        if (currentWord.isEmpty())
        {
            reset();
            return;
        }

        completationSListView.getItems().clear();

        for (var completation : completations)
        {
            final var completationValue = completation.getValue();
            
            if (completationValue.contains(currentWord))
            {
                completationSListView.getItems().add(completation);
            }
        }

        if (!completationSListView.getItems().isEmpty())
        {
            completationSListView.getSelectionModel().selectFirst();

            completationsBox.show(
                    sealCodeArea, sealCodeArea.getCaretBounds().get().getMaxX(), // Posicion X del caret
                    sealCodeArea.getCaretBounds().get().getMaxY()); // Posicion Y del caret

            sealCodeArea.requestFocus();
            return;
        }

        reset();
        sealCodeArea.requestFocus();
    }
    
    private String readLastWord()
    {
        final var text = sealCodeArea.getText();
        final var caretPosition = sealCodeArea.getCaretPosition();
        
        for (int i = caretPosition - 1; i > 0; i--)
        {
            if (text.charAt(i) == ' ' || text.charAt(i) == '\n' 
                    || text.charAt(i) == '\t')
            {
                return text.substring(i + 1, caretPosition);
            }
        }

        return text.substring(0, caretPosition);
    }
}
