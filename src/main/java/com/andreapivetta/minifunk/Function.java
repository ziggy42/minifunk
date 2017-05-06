package com.andreapivetta.minifunk;

/**
 * Created by andrea on 5/6/17.
 */
public interface Function<T, R> {
    R apply(T value);
}
