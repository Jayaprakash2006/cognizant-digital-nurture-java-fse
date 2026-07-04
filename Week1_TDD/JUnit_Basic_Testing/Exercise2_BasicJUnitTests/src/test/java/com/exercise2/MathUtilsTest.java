package com.exercise2;

import org.junit.Test;
import static org.junit.Assert.*;

public class MathUtilsTest {

    @Test
    public void testAdd() {
        MathUtils mathUtils = new MathUtils();
        assertEquals(10, mathUtils.add(4, 6));
    }

    @Test
    public void testSubtract() {
        MathUtils mathUtils = new MathUtils();
        assertEquals(3, mathUtils.subtract(8, 5));
    }

    @Test
    public void testMultiply() {
        MathUtils mathUtils = new MathUtils();
        assertEquals(20, mathUtils.multiply(4, 5));
    }

    @Test
    public void testDivide() {
        MathUtils mathUtils = new MathUtils();
        assertEquals(2.5, mathUtils.divide(5, 2), 0.001);
    }

    @Test(expected = ArithmeticException.class)
    public void testDivideByZero() {
        MathUtils mathUtils = new MathUtils();
        mathUtils.divide(10, 0);
    }

    @Test
    public void testIsEven() {
        MathUtils mathUtils = new MathUtils();
        assertTrue(mathUtils.isEven(4));
        assertFalse(mathUtils.isEven(7));
    }
}
