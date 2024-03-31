package com.lebastudios.stexteditor.app.txtformatter;


import java.io.File;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.lebastudios.stexteditor.TextEditorApplication;
import com.lebastudios.stexteditor.app.FileOperation;
import com.lebastudios.stexteditor.app.FilePaths;
import com.lebastudios.stexteditor.app.Resources;
import javafx.concurrent.Task;

import javafx.stage.WindowEvent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;

public class KeyWordHighlighter
{
    public JSONPatterns patterns;

    public static class PatternInfo
    {
        public String name;
        public String pattern;
        public String colourleablePattern;
    }

    public static class JSONPatterns
    {
        public PatternInfo[] patternsInfo;
        
        public PatternInfo getPatternInfo(String name)
        {
            for (var variable : patternsInfo)
            {
                if (variable.name.equals(name))
                {
                    return variable;
                }
            }
            
            return null;
        }
    }
    
    private Pattern pattern;

    private CodeArea codeArea;
    private ExecutorService executor;
    private final String extension;

    public KeyWordHighlighter(CodeArea codeArea, String extension)
    {
        this.extension = extension;
        
        this.patterns = new Gson().fromJson(Resources.getHighlightingRules(extension), JSONPatterns.class);
        
        patternsCreator();

        this.codeArea = codeArea;
        executor = Executors.newSingleThreadExecutor();
        startTask();

        // Resaltado tan pronto se abre el archivo
        applyHighlighting(computeHighlighting(codeArea.getText()));
    }

    /**
     * Crea los patrones de resaltado de sintaxis
     */
    private void patternsCreator()
    {
        StringBuilder patternString = new StringBuilder();
        
        for (var patternInfo : patterns.patternsInfo)
        {
            patternString.append("(?<").append(patternInfo.name).append(">").
                    append(patternInfo.pattern).append(")").append("|");
        }
        
        // Eliminar el último "|"
        String keywordPattern = patternString.substring(0, patternString.length() - 1);
        
        pattern = Pattern.compile(keywordPattern);
    }

    private String getPatternName(Matcher matcher)
    {
        for (var variable : patterns.patternsInfo)
        {
            final var patternName = variable.name;
            
            if (matcher.group(patternName) != null)
            {
                return patternName;
            }
        }
        
        return "default.css";
    }

    private void startTask()
    {
        Subscription cleanupWhenDone = codeArea.multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .retainLatestUntilLater(executor)
                .supplyTask(this::computeHighlightingAsync)
                .awaitLatest(codeArea.multiPlainChanges())
                .filterMap(t ->
                {
                    if (t.isSuccess())
                    {
                        return Optional.of(t.get());
                    }
                    else
                    {
                        t.getFailure().printStackTrace();
                        return Optional.empty();
                    }
                })
                .subscribe(this::applyHighlighting);

        // Limpiar el executor cuando se cierre la ventana
        TextEditorApplication.getStage().addEventHandler(WindowEvent.WINDOW_HIDING, e -> cleanupWhenDone.unsubscribe());
        codeArea.addEventHandler(WindowEvent.WINDOW_HIDING, e -> cleanupWhenDone.unsubscribe());
    }

    private Task<StyleSpans<Collection<String>>> computeHighlightingAsync()
    {
        String text = codeArea.getText();

        Task<StyleSpans<Collection<String>>> task = new Task<>()
        {
            @Override
            protected StyleSpans<Collection<String>> call()
            {
                return computeHighlighting(text);
            }
        };
        executor.execute(task);
        return task;
    }

    private void applyHighlighting(StyleSpans<Collection<String>> highlighting)
    {
        codeArea.setStyleSpans(0, highlighting);
    }

    private StyleSpans<Collection<String>> computeHighlighting(String text)
    {
        Matcher matcher = pattern.matcher(text);
        int lastKwEnd = 0;
        
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        
        while (matcher.find())
        {
            final var patternName = getPatternName(matcher);

            spansBuilder.add(Collections.emptyList(),
                    (matcher.start()) - (lastKwEnd));
            
            computeHighlightingOnColourleable(patternName, matcher.group(), spansBuilder);
            
            lastKwEnd = matcher.end();
        }
        
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
    
    private void computeHighlightingOnColourleable(String patterName, String text,
            StyleSpansBuilder<Collection<String>> spansBuilder)
    {
        String coloureablePattern = patterns.getPatternInfo(patterName).colourleablePattern;
        
        if (coloureablePattern == null || coloureablePattern.isEmpty()) 
        {
            spansBuilder.add(Collections.singleton(patterName), text.length());
            return;
        }
        
        Matcher matcher = Pattern.compile(coloureablePattern).matcher(text);
        int innerLatsKwEnd = 0;
        
        while (matcher.find())
        {
            spansBuilder.add(Collections.emptyList(), matcher.start() - innerLatsKwEnd);
            spansBuilder.add(Collections.singleton(patterName), matcher.end() - matcher.start());
            
            innerLatsKwEnd = matcher.end();
        }
        
        spansBuilder.add(Collections.emptyList(), text.length() - innerLatsKwEnd);
    }
}
