package com.andreapivetta.minifunk;

/**
 * Created by andrea on 5/6/17.
 */
public interface Consumer<T> {
    void accept(T value);
}
