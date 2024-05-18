package com.lebastudios.sealcode.global;

import org.fxmisc.richtext.model.ReadOnlyStyledDocument;
import org.fxmisc.richtext.model.SegmentOps;
import org.fxmisc.richtext.model.StyledDocument;

import java.util.Collection;
import java.util.Collections;

public class DocumentsOperations
{
    private DocumentsOperations() {}
    
    public static StyledDocument<Collection<String>, String, Collection<String>> createStyledDocument(String text)
    {
        return ReadOnlyStyledDocument.fromString(
                text, Collections.singleton(""), Collections.singleton(""), SegmentOps.styledTextOps()
        );
    }

    public static StyledDocument<Collection<String>, String, Collection<String>> createStyledDocument()
    {
        return createStyledDocument("");
    }
}
