package com.lebastudios.sealcode.events;

import org.fxmisc.richtext.model.StyleSpans;

import java.util.Collection;

public interface IStyleSpansRequest
{
    void invoke(StyleSpans<Collection<String>> styleSpans, String text, String filExtension);
}
