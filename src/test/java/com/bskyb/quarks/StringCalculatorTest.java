package com.bskyb.quarks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringCalculatorTest {
    StringCalculator calcluator = new StringCalculator();
    @Test
    void emptyStringTest() {
        assertEquals(0, calcluator.add(""));
    }

    @Test
    void singularNumberTest() {
        assertEquals(1, calcluator.add("1"));
        assertEquals(2, calcluator.add("2"));
        assertEquals(3, calcluator.add("3"));
    }

    @Test
    void separatedByComaTest() {
        assertEquals(3, calcluator.add("1,2"));
        assertEquals(12, calcluator.add("1,2,3,6"));
    }

    @Test
    void separatedByIntroTest() {
        assertEquals(3, calcluator.add("1\n2"));
        assertEquals(6, calcluator.add("1\n2,3"));
        assertEquals(5, calcluator.add("\n2,3"));
    }

    @Test
    void delimeterConditionTest(){
        assertEquals(3,calcluator.add("//;\n1;2"));
    }

    @Test
    void negativeNumberConditionTest(){
        assertThrowsExactly(IllegalArgumentException.class, () -> calcluator.add("1,2,-3"));
        assertThrowsExactly(IllegalArgumentException.class, () -> calcluator.add("-1,-2,-3"));
    }

    @Test
    void moreThanOneThousandTest(){
        assertEquals(1003,calcluator.add("1,2,1000"));
        assertEquals(3,calcluator.add("1,2,1001"));
    }
    @Test
    void longDelimetersTest(){
        assertEquals(6,calcluator.add("//[***]\n1***2***3"));
    }

    @Test
    void diverseDelimetersTest(){
        assertEquals(24,calcluator.add("//[***] [@] [??]\n1***2***3??6@12"));
    }
}
