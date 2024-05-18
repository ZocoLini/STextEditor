package com.lebastudios.sealcode.custom.logic.database;

import java.util.function.Function;

public interface IDBManager<T>
{
    boolean connect(Function<T, Boolean> function);

    default boolean testConnection()
    {
        return connect(null);
    }
}
