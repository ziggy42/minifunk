package com.andreapivetta.minifunk;

import com.andreapivetta.minifunk.function.BiFunction;
import com.andreapivetta.minifunk.function.Consumer;
import com.andreapivetta.minifunk.function.Function;
import com.andreapivetta.minifunk.function.Predicate;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class EnumerableTest {

    @Test
    public void asList() throws Exception {
        List<String> strings = Enumerable
                .from(Arrays.asList("apple", "pear", "lemon"))
                .asList();

        assertNotNull(strings);
        assertEquals(3, strings.size());

        strings = Enumerable
                .from(new String[]{"apple", "pear", "lemon"})
                .asList();

        assertNotNull(strings);
        assertEquals(3, strings.size());
    }

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
                .reduce(0, new BiFunction<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer accumulator, Integer value) {
                        return accumulator + value;
                    }
                });

        assertNotNull(total);
        assertEquals(Integer.valueOf(10), total);

        String concat = Enumerable.from(Arrays.asList('a', 'b', 'c'))
                .reduce("", new BiFunction<Character, String>() {
                    @Override
                    public String apply(String accumulator, Character value) {
                        return accumulator + value;
                    }
                });

        assertNotNull(concat);
        assertEquals("abc", concat);
    }

    @Test
    public void sort() throws Exception {
        List<String> strings = Enumerable
                .from(Arrays.asList("Real Madrid", "Inter", "Milan", "Juventus"))
                .sort(new Comparator<String>() {
                    @Override
                    public int compare(String s, String t1) {
                        return s.compareTo(t1);
                    }
                })
                .asList();

        assertNotNull(strings);
        assertEquals(4, strings.size());
        assertEquals("Inter", strings.get(0));
    }

    @Test
    public void find() throws Exception {
        String value = Enumerable
                .from(Arrays.asList("Inter", "Milan", "Juventus"))
                .find(new Predicate<String>() {
                    @Override
                    public boolean test(String s) {
                        return s.startsWith("J");
                    }
                });

        assertNotNull(value);
        assertEquals("Juventus", value);

        value = Enumerable
                .from(Arrays.asList("Inter", "Milan", "Juventus"))
                .find(new Predicate<String>() {
                    @Override
                    public boolean test(String s) {
                        return s.startsWith("R");
                    }
                });

        assertNull(value);
    }

    @Test
    public void every() throws Exception {
        assertTrue(Enumerable.from(new Integer[]{1, 2, 3, 4})
                .every(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) {
                        return integer > 0;
                    }
                }));
    }

    @Test
    public void some() throws Exception {
        assertTrue(Enumerable.from(new Integer[]{1, 2, 3, 4})
                .some(new Predicate<Integer>() {
                          @Override
                          public boolean test(Integer integer) {
                              return integer > 2;
                          }
                      }
                ));
    }

    @Test
    public void count() throws Exception {
        assertEquals(4, Enumerable.from(Arrays.asList(1, 2, 3, 4)).count());
    }

    @Test
    public void testToString() throws Exception {
        Enumerable<String> enumerable = Enumerable
                .from(Arrays.asList("Real Madrid", "Inter", "Milan", "Juventus"));
        assertEquals("[Real Madrid, Inter, Milan, Juventus]", enumerable.toString());
    }
}