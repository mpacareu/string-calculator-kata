package com.bskyb.quarks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringCalculatorTest {

    StringCalculator calculator = new StringCalculator();

    // Step 1 & 2
    @Test
    void test1OneNumber() {
        assertEquals(1, calculator.add("1")); //asserTrue(calculator.add("1") == 1);
    }

    @Test
    void test1EmptyString() {
        assertEquals(0, calculator.add(""));
    }
    @Test
    void test1TwoNumbers() {
        assertEquals(3, calculator.add("1,2"));
    }

    @Test
    void test2MultipleNumbers() {
        assertEquals(10, calculator.add("1,2,3,4"));
    }

    @Test
    void test3NewSeparators() {
        assertEquals(6, calculator.add("1\n2,3"));
    }
}
