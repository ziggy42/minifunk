package com.andreapivetta.minifunk;

/**
 * Represents a function that accepts two arguments and produces a result.
 *
 * @author Andrea Pivetta
 */
public interface BiFunction<T, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param r the first function argument
     * @param t the second function argument
     * @return the function result
     */
    R apply(R r, T t);
}
