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

public class StreamTest {

    @Test
    public void allMatch() throws Exception {
        assertTrue(Stream.from(new Integer[]{1, 2, 3, 4})
                .allMatch(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) {
                        return integer > 0;
                    }
                }));
    }

    @Test
    public void anyMatch() throws Exception {
        assertTrue(Stream.from(new Integer[]{1, 2, 3, 4})
                .anyMatch(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) {
                        return integer > 2;
                    }
                }));
    }

    @Test
    public void distinct() throws Exception {
        List<String> strings = Stream.from(Arrays.asList("Inter", "Juventus", "Milan", "Juventus"))
                .distinct()
                .toList();

        assertNotNull(strings);
        assertEquals(3, strings.size());
        assertEquals(Arrays.asList("Inter", "Juventus", "Milan"), strings);
    }

    @Test
    public void count() throws Exception {
        assertEquals(4, Stream.from(Arrays.asList(1, 2, 3, 4)).count());
    }

    @Test
    public void filter() throws Exception {
        List<String> result = Stream
                .from(Arrays.asList("apple", "pear", "lemon"))
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String value) {
                        return value.length() > 4;
                    }
                })
                .toList();

        assertEquals(2, result.size());
        assertTrue(result.contains("apple"));
        assertTrue(result.contains("lemon"));
    }

    @Test
    public void findFirst() throws Exception {
        String value = Stream
                .from(Arrays.asList("Inter", "Milan", "Juventus"))
                .findFirst(new Predicate<String>() {
                    @Override
                    public boolean test(String s) {
                        return s.startsWith("J");
                    }
                });

        assertNotNull(value);
        assertEquals("Juventus", value);

        value = Stream
                .from(Arrays.asList("Inter", "Milan", "Juventus"))
                .findFirst(new Predicate<String>() {
                    @Override
                    public boolean test(String s) {
                        return s.startsWith("R");
                    }
                });

        assertNull(value);
    }

    @Test
    public void flatMap() throws Exception {
        class FootballClub {
            String name;
            List<Integer> titles = new ArrayList<Integer>();

            FootballClub(String name, List<Integer> titles) {
                this.name = name;
                this.titles = titles;
            }
        }

        List<FootballClub> clubs = Arrays.asList(new FootballClub("Milan", Arrays.asList(2011)),
                new FootballClub("Juventus", Arrays.asList(2012, 2013, 2014, 2015, 2016)));

        List<Integer> titles = Stream.from(clubs)
                .flatMap(new Function<FootballClub, Stream<Integer>>() {
                    @Override
                    public Stream<Integer> apply(FootballClub footballClub) {
                        return Stream.from(footballClub.titles);
                    }
                })
                .toList();

        assertNotNull(titles);
        assertEquals(Arrays.asList(2011, 2012, 2013, 2014, 2015, 2016), titles);
    }

    @Test
    public void forEach() throws Exception {
        final List<String> result = new ArrayList<String>();
        Stream
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
    public void limit() throws Exception {
        List<Integer> limited = Stream.from(Arrays.asList(1, 2, 3)).limit(2).toList();

        assertNotNull(limited);
        assertEquals(2, limited.size());
        assertEquals(Integer.valueOf(1), limited.get(0));

        limited = Stream.from(Arrays.asList(1, 2, 3)).limit(3).toList();

        assertNotNull(limited);
        assertEquals(3, limited.size());
        assertEquals(Integer.valueOf(1), limited.get(0));

        limited = Stream.from(Arrays.asList(1, 2, 3)).limit(4).toList();

        assertNotNull(limited);
        assertEquals(3, limited.size());
        assertEquals(Integer.valueOf(1), limited.get(0));
    }

    @Test
    public void map() throws Exception {
        List<Integer> result = Stream
                .from(Arrays.asList("apple", "pear", "lemon"))
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String value) {
                        return value.length();
                    }
                })
                .toList();

        assertEquals(3, result.size());
        assertEquals(Integer.valueOf(5), result.get(0));
        assertEquals(Integer.valueOf(4), result.get(1));
        assertEquals(Integer.valueOf(5), result.get(2));
    }

    @Test
    public void reduce() throws Exception {
        Integer total = Stream
                .from(Arrays.asList(1, 2, 3, 4))
                .reduce(0, new BiFunction<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer accumulator, Integer value) {
                        return accumulator + value;
                    }
                });

        assertNotNull(total);
        assertEquals(Integer.valueOf(10), total);

        String concat = Stream.from(Arrays.asList('a', 'b', 'c'))
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
    public void sorted() throws Exception {
        List<String> strings = Stream
                .from(Arrays.asList("Real Madrid", "Inter", "Milan", "Juventus"))
                .sorted(new Comparator<String>() {
                    @Override
                    public int compare(String s, String t1) {
                        return s.compareTo(t1);
                    }
                })
                .toList();

        assertNotNull(strings);
        assertEquals(4, strings.size());
        assertEquals("Inter", strings.get(0));
    }

    @Test
    public void toList() throws Exception {
        List<String> strings = Stream
                .from(Arrays.asList("apple", "pear", "lemon"))
                .toList();

        assertNotNull(strings);
        assertEquals(3, strings.size());

        strings = Stream
                .from(new String[]{"apple", "pear", "lemon"})
                .toList();

        assertNotNull(strings);
        assertEquals(3, strings.size());
    }

    @Test
    public void testToString() throws Exception {
        Stream<String> stream = Stream
                .from(Arrays.asList("Real Madrid", "Inter", "Milan", "Juventus"));
        assertEquals("[Real Madrid, Inter, Milan, Juventus]", stream.toString());
    }
}