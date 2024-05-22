package com.lebastudios.sealcode.core.os;

public class OperativeSystem
{
    private static IOperativeSystem actualOS;

    public static IOperativeSystem getActualOS()
    {
        if (actualOS == null)
        {
            actualOS = detectOS();
        }

        return actualOS;
    }

    public static String fileSeparator()
    {
        return getActualOS().fileSeparator();
    }
    
    private static IOperativeSystem detectOS()
    {
        String osName = System.getProperty("os.styleClass");

        osName = osName.toLowerCase();

        if (osName.contains("win")) return new Windows();

        if (osName.contains("nux")) return new Linux();

        if (osName.contains("mac")) return new Mac();

        return null;
    }
}
