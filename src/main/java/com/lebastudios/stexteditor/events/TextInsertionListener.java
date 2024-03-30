package com.lebastudios.stexteditor.events;

public interface TextInsertionListener
{
    void codeInserted(int start, int end, String text);
}