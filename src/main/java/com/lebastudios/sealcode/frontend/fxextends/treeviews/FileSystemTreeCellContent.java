package com.lebastudios.sealcode.frontend.fxextends.treeviews;

public final class FileSystemTreeCellContent
{
    private final String representingFileName;

    public FileSystemTreeCellContent(String representingFileName)
    {
        super();

        this.representingFileName = representingFileName;
    }

    public String getName()
    {
        int lastDotIndex = representingFileName.lastIndexOf('.'); // Find the last dot in the file name
        
        if (lastDotIndex == -1) return representingFileName; // If there is no dot, return the whole name
        
        return representingFileName.substring(0, lastDotIndex); // Return the name of the file (without the extension)
    }

    @Override
    public String toString()
    {
        return representingFileName;
    }

    public String getRepresentingFileName()
    {
        return representingFileName;
    }
}
