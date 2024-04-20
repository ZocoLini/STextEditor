package com.lebastudios.sealcode.frontend.fxextends.treeviews;

public record FileSystemTreeCellContent(String representingFileName)
{
    public String getName()
    {
        int lastDotIndex = representingFileName.lastIndexOf('.'); // Find the last dot in the file name

        if (lastDotIndex == -1) return representingFileName; // If there is no dot, return the whole name
        if (lastDotIndex == 0)
            lastDotIndex = representingFileName.length(); // If the dot is the first character, return the whole name

        return representingFileName.substring(0, lastDotIndex); // Return the name of the file (without the extension)
    }

    @Override
    public String toString()
    {
        return representingFileName;
    }
}
