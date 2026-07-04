package com.exercise3;

import org.junit.Test;
import static org.junit.Assert.*;

public class AssertionsTest {

    @Test
    public void testAssertions() {
        assertEquals(5, 2 + 3);

        assertTrue(5 > 3);

        assertFalse(5 < 3);

        assertNull(null);

        assertNotNull(new Object());
    }

    @Test
    public void testAssertArrayEquals() {
        int[] expected = {1, 2, 3};
        int[] actual   = {1, 2, 3};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testAssertSame() {
        String str = "JUnit";
        assertSame(str, str);
    }

    @Test
    public void testAssertNotSame() {
        assertNotSame(new Object(), new Object());
    }
}
