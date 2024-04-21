package com.lebastudios.sealcode.applogic.txtmod;

public interface ITextMod
{
    /**
     * This method cointains information about the text that was replaced
     *
     * @param start   Where the text replacement started
     * @param end     Where the text replacement ended
     * @param oldText The text that's going to be replaced
     * @param newText The text that's going to replace the old text
     * @return The new text with modifications if needed
     */
    String textModified(Integer start, Integer end, String oldText, String newText);
}
