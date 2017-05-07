package com.andreapivetta.minifunk.function;

/**
 * Represents a function that accepts one argument and produces a result.
 *
 * @author Andrea Pivetta
 */
public interface Function<T, R> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     */
    R apply(T t);
}
