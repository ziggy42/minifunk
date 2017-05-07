package com.andreapivetta.minifunk.function;

/**
 * Represents a predicate (boolean-valued function) of one argument.
 *
 * @author Andrea Pivetta
 */
public interface Predicate<T> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param t the input argument
     * @return true if the input argument matches the predicate, otherwise false
     */
    boolean test(T t);
}
