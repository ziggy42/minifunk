package com.andreapivetta.minifunk;

import com.andreapivetta.minifunk.function.BiFunction;
import com.andreapivetta.minifunk.function.Consumer;
import com.andreapivetta.minifunk.function.Function;
import com.andreapivetta.minifunk.function.Predicate;

import java.util.*;

/**
 * A sequence of elements supporting different aggregate operations.
 *
 * @author Andrea Pivetta
 */
public final class Enumerable<T> {

    private List<T> list;

    private Enumerable(List<T> list) {
        this.list = list;
    }

    /**
     * Creates an {@link Enumerable} instance from a List<T>
     *
     * @param list the starting list
     * @return the {@link Enumerable} instance
     * @throws IllegalArgumentException if list is null
     */
    public static <T> Enumerable<T> from(List<T> list) {
        if (list == null)
            throw new IllegalArgumentException("list must not be null");
        return new Enumerable<T>(list);
    }

    /**
     * Creates an {@link Enumerable} instance from an T[]
     *
     * @param array the starting array
     * @return the {@link Enumerable} instance
     * @throws IllegalArgumentException if array is null
     */
    public static <T> Enumerable<T> from(T[] array) {
        if (array == null)
            throw new IllegalArgumentException("array must not be null");
        return new Enumerable<T>(Arrays.asList(array));
    }

    /**
     * Returns a {@link List} from the current {@link Enumerable} instance
     *
     * @return the list
     */
    public List<T> asList() {
        return this.list;
    }

    /**
     * Performs the given action for each element of the {@link Enumerable} until all elements have been processed or
     * the action throws an exception. Exceptions thrown by the action are relayed to the caller.
     *
     * @param action the action to be performed for each element
     * @throws IllegalArgumentException if action is null
     */
    public void forEach(Consumer<T> action) {
        if (action == null)
            throw new IllegalArgumentException("action must not be null");

        for (T t : this.list)
            action.accept(t);
    }

    /**
     * Returns an {@link Enumerable} consisting of the elements of this {@link Enumerable} that match the given
     * predicate.
     *
     * @param predicate a predicate to apply to each element to determine if it should be included
     * @return the new {@link Enumerable}
     * @throws IllegalArgumentException if predicate is null
     */
    public Enumerable<T> filter(Predicate<T> predicate) {
        if (predicate == null)
            throw new IllegalArgumentException("predicate must not be null");

        List<T> filtered = new ArrayList<T>();
        for (T t : list)
            if (predicate.test(t))
                filtered.add(t);
        return new Enumerable<T>(filtered);
    }

    /**
     * Returns an {@link Enumerable} consisting of the results of applying the given function to the elements of this
     * {@link Enumerable}.
     *
     * @param mapper a function to apply to each element
     * @param <R>    The element type of the new {@link Enumerable}
     * @return the new {@link Enumerable}
     * @throws IllegalArgumentException if mapper is null
     */
    public <R> Enumerable<R> map(Function<T, R> mapper) {
        if (mapper == null)
            throw new IllegalArgumentException("mapper must not be null");

        List<R> result = new ArrayList<R>();
        for (T t : this.list)
            result.add(mapper.apply(t));
        return new Enumerable<R>(result);
    }

    /**
     * Performs a reduction on the elements of this {@link Enumerable}, using the provided initial accumulator value
     * and an associative accumulation function, and returns the reduced value.
     *
     * @param accumulator the initial accumulator value
     * @param operator    a function for combining two values
     * @param <R>         the element type of the reduced value
     * @return the result of the reduction
     * @throws IllegalArgumentException if operator is null
     */
    public <R> R reduce(R accumulator, BiFunction<T, R> operator) {
        if (operator == null)
            throw new IllegalArgumentException("operator must not be null");

        for (T value : this.list)
            accumulator = operator.apply(accumulator, value);
        return accumulator;
    }

    /**
     * Returns an {@link Enumerable} consisting of the sorted version of the current one.
     *
     * @param comparator the comparator function
     * @return the result of the sort
     * @throws IllegalArgumentException if comparator is null
     */
    public Enumerable<T> sort(Comparator<T> comparator) {
        if (comparator == null)
            throw new IllegalArgumentException("comparator must not be null");

        List<T> copy = new ArrayList<T>(this.list);
        Collections.sort(copy, comparator);
        return new Enumerable<T>(copy);
    }

    /**
     * Returns the value of the first element that satisfies the provided testing function. Otherwise null is returned.
     *
     * @param predicate a predicate to apply to each element to determine if it should be returned
     * @return the founded element
     * @throws IllegalArgumentException if predicate is null
     */
    public T find(Predicate<T> predicate) {
        if (predicate == null)
            throw new IllegalArgumentException("predicate must not be null");

        for (T t : this.list)
            if (predicate.test(t))
                return t;
        return null;
    }

    /**
     * Returns true if every element satisfies the provided testing function.
     *
     * @param predicate a predicate to apply to each element
     * @return true if every element satisfies the testing function.
     * @throws IllegalArgumentException if predicate is null
     */
    public boolean every(Predicate<T> predicate) {
        if (predicate == null)
            throw new IllegalArgumentException("predicate must not be null");

        for (T t : this.list)
            if (!predicate.test(t))
                return false;
        return true;
    }

    /**
     * Returns true if at least one element satisfies the provided testing function.
     *
     * @param predicate a predicate to apply to each element
     * @return true at least one element satisfies the testing function
     * @throws IllegalArgumentException if predicate is null
     */
    public boolean some(Predicate<T> predicate) {
        if (predicate == null)
            throw new IllegalArgumentException("predicate must not be null");

        for (T t : this.list)
            if (predicate.test(t))
                return true;
        return false;
    }

    /**
     * Counts the elements contained by this {@link Enumerable}
     *
     * @return the count
     */
    public int count() {
        return this.list.size();
    }

    @Override
    public String toString() {
        return Arrays.toString(this.list.toArray());
    }
}
