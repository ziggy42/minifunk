package com.andreapivetta.minifunk;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by andrea on 5/6/17.
 */
public class EnumerableTest {

    @Test
    public void forEach() throws Exception {
        final List<String> result = new ArrayList<String>();
        Enumerable
                .from(Arrays.asList("apple", "pear", "lemon"))
                .forEach(new Consumer<String>() {
                    @Override
                    public void accept(String value) {
                        result.add(value);
                    }
                });

        assertEquals(3, result.size());
        assertEquals("apple", result.get(0));
        assertEquals("pear", result.get(1));
        assertEquals("lemon", result.get(2));
    }

    @Test
    public void filter() throws Exception {
        List<String> result = Enumerable
                .from(Arrays.asList("apple", "pear", "lemon"))
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String value) {
                        return value.length() > 4;
                    }
                })
                .asList();

        assertEquals(2, result.size());
        assertTrue(result.contains("apple"));
        assertTrue(result.contains("lemon"));
    }

    @Test
    public void map() throws Exception {
        List<Integer> result = Enumerable
                .from(Arrays.asList("apple", "pear", "lemon"))
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String value) {
                        return value.length();
                    }
                })
                .asList();

        assertEquals(3, result.size());
        assertEquals(Integer.valueOf(5), result.get(0));
        assertEquals(Integer.valueOf(4), result.get(1));
        assertEquals(Integer.valueOf(5), result.get(2));
    }

    @Test
    public void reduce() throws Exception {
        Integer total = Enumerable
                .from(Arrays.asList(1, 2, 3, 4))
                .reduce(0, new BinaryOperator<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer accumulator, Integer value) {
                        return accumulator + value;
                    }
                });

        assertNotNull(total);
        assertEquals(Integer.valueOf(10), total);

        String concat = Enumerable.from(Arrays.asList('a', 'b', 'c'))
                .reduce("", new BinaryOperator<Character, String>() {
                    @Override
                    public String apply(String accumulator, Character value) {
                        return accumulator + value;
                    }
                });

        assertNotNull(concat);
        assertEquals("abc", concat);
    }
}