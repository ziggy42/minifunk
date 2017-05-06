package com.andreapivetta.minifunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by andrea on 5/6/17.
 */
public final class Enumerable<T> {

    private List<T> list;

    public static <T> Enumerable<T> from(List<T> list) {
        return new Enumerable<T>(list);
    }

    public static <T> Enumerable<T> from(T[] array) {
        return new Enumerable<T>(Arrays.asList(array));
    }

    private Enumerable(List<T> list) {
        this.list = list;
    }

    public List<T> asList() {
        return this.list;
    }

    public void forEach(Consumer<T> consumer) {
        for (T t : this.list)
            consumer.accept(t);
    }

    public Enumerable<T> filter(Predicate<T> predicate) {
        List<T> filtered = new ArrayList<T>();
        for (T t : list)
            if (predicate.test(t))
                filtered.add(t);
        this.list = filtered;
        return this;
    }

    public <R> Enumerable<R> map(Function<T, R> function) {
        List<R> result = new ArrayList<R>();
        for (T t : this.list)
            result.add(function.apply(t));
        return new Enumerable<R>(result);
    }

    public <R> R reduce(R accumulator, BinaryOperator<T, R> operator) {
        for (T value : this.list)
            accumulator = operator.apply(accumulator, value);
        return accumulator;
    }
}
