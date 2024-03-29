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
import javafx.concurrent.Task;

import javafx.stage.WindowEvent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;

public class KeyWordHighlighter
{
    // TODO: Pendiente replantear, de forma que sea el json quien defina diferentes 
    //  objetos donde cada uno contendra, el patorn de resaltado, el nombre de la clase de estilos
    //  y ... lo que se me ocurra, está por determinar

    private static final String PROG_LANG_PATH = "config/prog-lang/";

    public PatternsFile patterns;

    public static class PatternsFile
    {
        public String[] keywords;
        public String methodPattern;
        public String parenPattern;
        public String bracePattern;
        public String bracketPattern;
        public String semicolonPattern;
        public String stringPattern;
        public String commentPattern;
        public String tagPattern;
    }

    private Pattern pattern;

    private CodeArea codeArea;
    private ExecutorService executor;
    private String extension;

    public KeyWordHighlighter(CodeArea codeArea, String extension)
    {
        this.extension = extension;
        String path = PROG_LANG_PATH + extension + ".json";
        try
        {
            this.patterns = new Gson().fromJson(FileOperation.read(new File(path)), PatternsFile.class);
        }
        catch (Exception e)
        {
            System.err.println("Error al leer el archivo de patrones de resaltado de sintaxis " +
                    "para la extensión " + extension + ". No se leaplicará resaltado de sintaxis.");
            return;
        }
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
        String keywordPattern = "\\b(" + String.join("|", patterns.keywords) + ")\\b";

        pattern = Pattern.compile(
                "(?<KEYWORD>" + keywordPattern + ")"
                        + "|(?<PAREN>" + patterns.parenPattern + ")"
                        + "|(?<BRACE>" + patterns.bracePattern + ")"
                        + "|(?<BRACKET>" + patterns.bracketPattern + ")"
                        + "|(?<SEMICOLON>" + patterns.semicolonPattern + ")"
                        + "|(?<STRING>" + patterns.stringPattern + ")"
                        + "|(?<COMMENT>" + patterns.commentPattern + ")"
                        + "|(?<METHOD>" + patterns.methodPattern + ")"
                        + "|(?<TAG>" + patterns.tagPattern + ")"
        );
    }

    private String getStyleClass(Matcher matcher)
    {
        String styleClass =
                matcher.group("KEYWORD") != null ? "keyword" :
                matcher.group("PAREN") != null ? "paren" :
                matcher.group("BRACE") != null ? "brace" :
                matcher.group("BRACKET") != null ? "bracket" :
                matcher.group("SEMICOLON") != null ? "semicolon" :
                matcher.group("STRING") != null ? "string" :
                matcher.group("COMMENT") != null ? "comment" :
                matcher.group("METHOD") != null ? "method" :
                matcher.group("TAG") != null ? "tag" :
                null; /* never happens */
        assert styleClass != null;
        return concatExtensionWithStyleClass(styleClass);
    }

    private String concatExtensionWithStyleClass(String styleClass)
    {
        return extension + "-" + styleClass;
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
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while (matcher.find())
        {
            final var styleClass = getStyleClass(matcher);

            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }


}
