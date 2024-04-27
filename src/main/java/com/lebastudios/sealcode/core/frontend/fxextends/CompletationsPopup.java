package com.lebastudios.sealcode.core.frontend.fxextends;

import com.lebastudios.sealcode.config.CompletationsMap;
import com.lebastudios.sealcode.events.AppEvents;
import com.lebastudios.sealcode.util.Completation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Popup;

import java.util.TreeSet;

public class CompletationsPopup extends Popup implements ChangeListener<String>
{
    private final TreeSet<Completation> completations;
    
    private final ListView<Completation> completationSListView = new ListView<>();
    private final SealCodeArea sealCodeArea;
    
    private String currentWord = "";
    
    public CompletationsPopup(SealCodeArea sealCodeArea)
    {
        this.sealCodeArea = sealCodeArea;

        completations = CompletationsMap.getInstance().getCompletations(sealCodeArea.fileExtension);
        
        this.getContent().add(completationSListView);
        completationSListView.setMaxHeight(80);

        completationSListView.setCellFactory(param -> new CompletationListCell());
        
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
    
    private void reset()
    {
        completationSListView.getItems().clear();
        this.hide();
    }

    private void complete()
    {
        final var caretPosition = sealCodeArea.getCaretPosition();

        final var completation = completationSListView.getSelectionModel().getSelectedItem();

        if (completation == null) return;

        final var text = completation.getCompletation();

        sealCodeArea.replaceText(caretPosition - currentWord.substring(currentWord.lastIndexOf(".") + 1).length(), caretPosition, text);

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

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
    {
        currentWord = readLastWord();

        if (currentWord.isEmpty())
        {
            reset();
            return;
        }

        TreeSet<Completation> requestedCompletations = new TreeSet<>();
        Thread requestCompletations = new Thread(() -> AppEvents.onCompletationsRequest
                .invoke(requestedCompletations, sealCodeArea.getRepresentingFile(), 
                        sealCodeArea.fileExtension, currentWord, sealCodeArea.getText()));
        requestCompletations.start();
        
        completationSListView.getItems().clear();

        filterCompletations(currentWord, completations, completationSListView.getItems());
        
        try
        {
            requestCompletations.join();
        } catch (InterruptedException ignore) {}

        completationSListView.getItems().addAll(requestedCompletations);
        
        if (!completationSListView.getItems().isEmpty())
        {
            completationSListView.getSelectionModel().selectFirst();

            this.show(
                    sealCodeArea, sealCodeArea.getCaretBounds().get().getMaxX(), // Posicion X del caret
                    sealCodeArea.getCaretBounds().get().getMaxY()); // Posicion Y del caret

            sealCodeArea.requestFocus();
            return;
        }

        reset();
        sealCodeArea.requestFocus();
    }
    
    private static void filterCompletations(String currentWord, TreeSet<Completation> comToFilter, 
                                            ObservableList<Completation> filteredCom)
    {
        for (var completation : comToFilter)
        {
            final var completationValue = completation.getValue();

            if (completationValue.startsWith(currentWord))
            {
                filteredCom.addFirst(completation);
            } else if (completationValue.contains(currentWord))
            {
                filteredCom.add(completation);
            }
        }
    }
}
