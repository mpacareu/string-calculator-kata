package com.bskyb.quarks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    void test3NewDelimiter() {
        assertEquals(6, calculator.add("1\n2,3"));
    }

    @Test
    void test4ChangeDelimiter() {
        assertEquals(3, calculator.add("//;\n1;2"));
    }

    @Test
    void test5NegativeNumber() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> calculator.add("1,-2")); //Passem el codi sense executar pq no peti abans de cridar el mètode
        assertEquals("negatives not allowed: -2", exception.getMessage());
    }

    @Test
    void test5NegativeNumbers() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> calculator.add("1,-2,-3"));
        assertEquals("negatives not allowed: -2, -3", exception.getMessage());
    }

    @Test
    void test6BiggerThan1000() {
        assertEquals(2, calculator.add("2,1001")); //Amb espais peta al fer split()("2, 1001") Poc flexible
    }

    @Test
    void test6BiggerThan10002() {
        assertEquals(1002, calculator.add("2,1001,1000"));
    }

    @Test
    void test7DifferentDelimiter() {
        assertEquals(6, calculator.add("//[***]\n1***2***3"));
    }

    @Test
    void test7DifferentDelimiters() {
        assertEquals(5, calculator.add("//[;]\n2;3"));
    }

}
