package com.andreapivetta.minifunk;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by andrea on 5/6/17.
 */
public class EnumerableTest {
    @Test
    public void filter() throws Exception {
        String[] data = {"apple", "pear", "lemon"};

        List<String> result = Enumerable.from(data)
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
}