package com.andreapivetta.minifunk.function;

/**
 * Represents an operation that accepts a single input argument and returns no result.
 *
 * @author Andrea Pivetta
 */
public interface Consumer<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param value the input argument
     */
    void accept(T value);
}
