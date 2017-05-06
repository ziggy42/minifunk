package com.andreapivetta.minifunk;

/**
 * Created by andrea on 5/6/17.
 */
public interface BinaryOperator<T, R> {
    R apply(R accumulator, T value);
}
