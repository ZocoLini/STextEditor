package com.lebastudios.sealcode.core.logic;

public abstract class Inspector implements IInspector
{
    protected static Inspector inspector = null;

    public static Inspector getInspector()
    {
        if (inspector == null)
        {
            throw new IllegalCallerException("No ha sido definido un inspector global");
        }

        return inspector;
    }
}
