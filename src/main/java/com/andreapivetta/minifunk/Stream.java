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
public final class Stream<T> {

    private List<T> list;

    private Stream(List<T> list) {
        this.list = list;
    }

    /**
     * Creates an {@link Stream} instance from a List<T>
     *
     * @param list the starting list
     * @return the {@link Stream} instance
     * @throws IllegalArgumentException if list is null
     */
    public static <T> Stream<T> from(List<T> list) {
        if (list == null)
            throw new IllegalArgumentException("list must not be null");
        return new Stream<T>(list);
    }

    /**
     * Creates an {@link Stream} instance from an T[]
     *
     * @param array the starting array
     * @return the {@link Stream} instance
     * @throws IllegalArgumentException if array is null
     */
    public static <T> Stream<T> from(T[] array) {
        if (array == null)
            throw new IllegalArgumentException("array must not be null");
        return new Stream<T>(Arrays.asList(array));
    }

    /**
     * Returns whether all elements of this stream match the provided predicate.
     *
     * @param predicate a predicate to apply to each element
     * @return true if allMatch element satisfies the testing function.
     * @throws IllegalArgumentException if predicate is null
     */
    public boolean allMatch(Predicate<? super T> predicate) {
        if (predicate == null)
            throw new IllegalArgumentException("predicate must not be null");

        for (T t : this.list)
            if (!predicate.test(t))
                return false;
        return true;
    }

    /**
     * Returns whether any elements of this stream match the provided predicate.
     *
     * @param predicate a predicate to apply to each element
     * @return true at least one element satisfies the testing function
     * @throws IllegalArgumentException if predicate is null
     */
    public boolean anyMatch(Predicate<? super T> predicate) {
        if (predicate == null)
            throw new IllegalArgumentException("predicate must not be null");

        for (T t : this.list)
            if (predicate.test(t))
                return true;
        return false;
    }

    /**
     * Counts the elements contained by this {@link Stream}
     *
     * @return the count
     */
    public int count() {
        return this.list.size();
    }

    /**
     * Returns a stream consisting of the distinct elements of this stream.
     *
     * @return the new {@link Stream}
     */
    public Stream<T> distinct() {
        return new Stream<T>(new ArrayList<T>(new LinkedHashSet<T>(this.list)));
    }

    /**
     * Returns an {@link Stream} consisting of the elements of this {@link Stream} that match the given
     * predicate.
     *
     * @param predicate a predicate to apply to each element to determine if it should be included
     * @return the new {@link Stream}
     * @throws IllegalArgumentException if predicate is null
     */
    public Stream<T> filter(Predicate<? super T> predicate) {
        if (predicate == null)
            throw new IllegalArgumentException("predicate must not be null");

        List<T> filtered = new ArrayList<T>();
        for (T t : list)
            if (predicate.test(t))
                filtered.add(t);
        return new Stream<T>(filtered);
    }

    /**
     * Returns the value of the first element that satisfies the provided testing function. Otherwise null is returned.
     *
     * @param predicate a predicate to apply to each element to determine if it should be returned
     * @return the founded element
     * @throws IllegalArgumentException if predicate is null
     */
    public T findFirst(Predicate<? super T> predicate) {
        if (predicate == null)
            throw new IllegalArgumentException("predicate must not be null");

        for (T t : this.list)
            if (predicate.test(t))
                return t;
        return null;
    }

    /**
     * Returns a stream consisting of the results of replacing each element of this stream with the contents of a
     * mapped stream produced by applying the provided mapping function to each element.
     *
     * @param mapper a function to apply to each element
     * @param <R>    The element type of the new {@link Stream}
     * @return the new {@link Stream}
     * @throws IllegalArgumentException if mapper is null
     */
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        if (mapper == null)
            throw new IllegalArgumentException("mapper must not be null");

        List<R> mapped = new ArrayList<R>();
        for (T t : this.list)
            mapped.addAll(mapper.apply(t).toList());
        return new Stream<R>(mapped);
    }

    /**
     * Performs the given action for each element of the {@link Stream} until all elements have been processed or
     * the action throws an exception. Exceptions thrown by the action are relayed to the caller.
     *
     * @param action the action to be performed for each element
     * @throws IllegalArgumentException if action is null
     */
    public void forEach(Consumer<? super T> action) {
        if (action == null)
            throw new IllegalArgumentException("action must not be null");

        for (T t : this.list)
            action.accept(t);
    }

    /**
     * Returns a stream consisting of the elements of this stream, truncated to be no longer than maxSize in length.
     *
     * @param maxSize the number of elements the stream should be limited to
     * @return the new {@link Stream}
     * @throws IllegalArgumentException if maxSize is less than 0
     */
    public Stream<T> limit(int maxSize) {
        if (maxSize < 0)
            throw new IllegalArgumentException("maxSize must be positive");

        if (this.list.size() < maxSize)
            return new Stream<T>(this.list);
        return new Stream<T>(this.list.subList(0, maxSize));
    }

    /**
     * Returns an {@link Stream} consisting of the results of applying the given function to the elements of this
     * {@link Stream}.
     *
     * @param mapper a function to apply to each element
     * @param <R>    The element type of the new {@link Stream}
     * @return the new {@link Stream}
     * @throws IllegalArgumentException if mapper is null
     */
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        if (mapper == null)
            throw new IllegalArgumentException("mapper must not be null");

        List<R> result = new ArrayList<R>();
        for (T t : this.list)
            result.add(mapper.apply(t));
        return new Stream<R>(result);
    }

    /**
     * Performs a reduction on the elements of this {@link Stream}, using the provided initial accumulator value
     * and an associative accumulation function, and returns the reduced value.
     *
     * @param accumulator the initial accumulator value
     * @param operator    a function for combining two values
     * @param <R>         the element type of the reduced value
     * @return the result of the reduction
     * @throws IllegalArgumentException if operator is null
     */
    public <R> R reduce(R accumulator, BiFunction<? super T, R> operator) {
        if (operator == null)
            throw new IllegalArgumentException("operator must not be null");

        for (T value : this.list)
            accumulator = operator.apply(accumulator, value);
        return accumulator;
    }

    /**
     * Returns an {@link Stream} consisting of the sorted version of the current one.
     *
     * @param comparator the comparator function
     * @return the result of the sorted
     * @throws IllegalArgumentException if comparator is null
     */
    public Stream<T> sorted(Comparator<? super T> comparator) {
        if (comparator == null)
            throw new IllegalArgumentException("comparator must not be null");

        List<T> copy = new ArrayList<T>(this.list);
        Collections.sort(copy, comparator);
        return new Stream<T>(copy);
    }

    /**
     * Returns a {@link List} from the current {@link Stream} instance
     *
     * @return the list
     */
    public List<T> toList() {
        return this.list;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.list.toArray());
    }
}
