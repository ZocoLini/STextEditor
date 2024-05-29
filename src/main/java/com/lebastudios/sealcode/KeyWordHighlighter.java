package com.lebastudios.sealcode;


import com.lebastudios.sealcode.frontend.SealCodeArea;
import com.lebastudios.sealcode.FilePaths;
import com.lebastudios.sealcode.fileobj.HighlightingRulesJSON;
import com.lebastudios.sealcode.fileobj.JsonFile;
import com.lebastudios.sealcode.events.AppEvents;
import com.lebastudios.sealcode.FileOperation;
import javafx.concurrent.Task;
import javafx.stage.WindowEvent;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;

import java.io.File;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeyWordHighlighter
{
    private JsonFile<HighlightingRulesJSON> patternsFileObj;
    private final SealCodeArea codeArea;
    private final ExecutorService executor;
    private Pattern pattern;

    // TODO: Crear multiples instancias quizas no es lo mejor.
    
    public KeyWordHighlighter(SealCodeArea codeArea)
    {
        patternsFileObj = new JsonFile<>(
                getHighlightingFile(codeArea.fileExtension),
                new HighlightingRulesJSON()
        );

        patternsFileObj.onRead.addListener(() -> {
            patternsCreator();
            applyHighlighting(computeHighlighting(codeArea.getText()));
        });

        AppEvents.onFileObjModified.addListener((path, fileObj) ->
        {
            if (!path.equals(FilePaths.getEquivalentExtensionsFile())) return;

            patternsFileObj = new JsonFile<>(
                    getHighlightingFile(codeArea.fileExtension),
                    new HighlightingRulesJSON()
            );
            
            patternsCreator();
            applyHighlighting(computeHighlighting(codeArea.getText()));
        });
        
        patternsCreator();

        this.codeArea = codeArea;
        executor = Executors.newSingleThreadExecutor();
        startTask();

        // Resaltado tan pronto se abre el archivo
        applyHighlighting(computeHighlighting(codeArea.getText()));
    }

    private static File getHighlightingFile(String extension)
    {
        File file = new File(FilePaths.getHighlightingRulesDir() + extension + ".json");

        if (file.exists()) return file;

        file = new File(FilePaths.getHighlightingRulesDir() + FileOperation.getEquivalentFileExtension(extension) + ".json");

        if (file.exists()) return file;

        return new File(FilePaths.getHighlightingRulesDir() + "default.json");
    }

    /**
     * Crea los patrones de resaltado de sintaxis
     */
    private void patternsCreator()
    {
        StringBuilder patternString = new StringBuilder();

        for (var patternInfo : patternsFileObj.get().rules)
        {
            patternString.append("(?<").append(patternInfo.name).append(">").
                    append(patternInfo.regex).append(")").append("|");
        }

        if (patternString.isEmpty())
        {
            pattern = null;
            return;
        }
        
        // Eliminar el último "|"
        String keywordPattern = patternString.substring(0, patternString.length() - 1);
        
        pattern = Pattern.compile(keywordPattern);
    }

    private String getPatternName(Matcher matcher)
    {
        for (var variable : patternsFileObj.get().rules)
        {
            final var patternName = variable.name;

            if (matcher.group(patternName) != null)
            {
                return variable.name;
            }
        }

        return "default.css";
    }

    private void startTask()
    {
        Subscription cleanupWhenDone = codeArea.multiPlainChanges()
                .successionEnds(Duration.ofMillis(100))
                .retainLatestUntilLater(executor)
                .supplyTask(this::computeHighlightingAsync)
                .awaitLatest(codeArea.multiPlainChanges())
                .filterMap(t ->
                {
                    if (t.isSuccess())
                    {
                        return Optional.of(t.get());
                    } else
                    {
                        t.getFailure().printStackTrace();
                        return Optional.empty();
                    }
                }).subscribe(this::applyHighlighting);

        codeArea.addEventHandler(WindowEvent.WINDOW_HIDING, e -> cleanupWhenDone.unsubscribe());
        codeArea.addEventHandler(WindowEvent.WINDOW_HIDDEN, e -> cleanupWhenDone.unsubscribe());
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
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        
        if (pattern == null) 
        {
            spansBuilder.add(Collections.singleton("default"), text.length());
            return spansBuilder.create();
        }
        
        Matcher matcher = pattern.matcher(text);
        int lastKwEnd = 0;

        while (matcher.find())
        {
            final var patternName = getPatternName(matcher);

            spansBuilder.add(Collections.singleton("default"),
                    (matcher.start()) - (lastKwEnd));

            computeHighlightingOnColourleable(patternName, matcher.group(), spansBuilder);

            lastKwEnd = matcher.end();
        }

        spansBuilder.add(Collections.singleton("default"), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private void computeHighlightingOnColourleable(String patterName, String text,
                                                   StyleSpansBuilder<Collection<String>> spansBuilder)
    {
        final var highlightingRule = patternsFileObj.get().getHighlightingRule(patterName);
        
        String coloureablePattern = highlightingRule.highlightRegex;

        if (coloureablePattern == null || coloureablePattern.isEmpty())
        {
            spansBuilder.add(Collections.singleton(highlightingRule.styleClass), text.length());
            return;
        }

        Matcher matcher = Pattern.compile(coloureablePattern).matcher(text);
        int innerLatsKwEnd = 0;

        while (matcher.find())
        {
            spansBuilder.add(Collections.singleton("default"), matcher.start() - innerLatsKwEnd);
            spansBuilder.add(Collections.singleton(highlightingRule.styleClass), matcher.end() - matcher.start());

            innerLatsKwEnd = matcher.end();
        }

        spansBuilder.add(Collections.singleton("default"), text.length() - innerLatsKwEnd);
    }
}
