package com.bskyb.quarks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringCalculatorTest {

    @Test
    void addIf0() {
        StringCalculator calculator = new StringCalculator();

        assertEquals(0, calculator.add(""), "Not returning 0 if empty string");
    }

    @Test
    void addNumbersSeparatedByComma() {
        StringCalculator calculator = new StringCalculator();

        assertEquals(4, calculator.add("4"), "Not returning the correct number");
        assertEquals(4, calculator.add("3,,1"), "Not returning the correct number");
        assertEquals(119, calculator.add("20,45,54"), "Not returning the correct number");
    }

    @Test
    void addNumbersSeparatedByNewLines() {
        StringCalculator calculator = new StringCalculator();

        assertEquals(6, calculator.add("1\n2,3"), "Not returning the correct number");
        assertEquals(6, calculator.add("1\n2,\n\n3"), "Not returning the correct number");
    }

    @Test
    void addWithDifferentDelimiters() {
        StringCalculator calculator = new StringCalculator();

        assertEquals(3, calculator.add("//;\n1;2"), "Not returning the correct number");
        assertEquals(3, calculator.add("// \n1 2"), "Not returning the correct number");
        assertEquals(3, calculator.add("///\n1/2"), "Not returning the correct number");
        assertEquals(3, calculator.add("//}\n1{2"), "Not returning the correct number");
    }

    @Test
    void throwExceptionWhenIsNegative() {
        StringCalculator calculator = new StringCalculator();
        assertThrows(IllegalArgumentException.class, () -> calculator.add("-1"));

    }

    @Test
    void numbersBiggerThan1000ShouldBeIgnored() {
        StringCalculator calculator = new StringCalculator();

        assertEquals(2, calculator.add("1001,2"), "Exception not thrown");
        assertEquals(1006, calculator.add("1000,2,5,999"), "Exception not thrown");
    }

    @Test
    void addWhenDelimiterItsMoreTHanOneCharacter() {
        StringCalculator calculator = new StringCalculator();

        assertEquals(6, calculator.add("//[´´´]\n1´´´2´´´3"), "Not returning the correct number");
        assertEquals(6, calculator.add("//[***]\n1***2***3"), "Not returning the correct number");
    }

    @Test
    void addWhenMoreThanOneDelimiter() {
        StringCalculator calculator = new StringCalculator();

        assertEquals(6, calculator.add("//[/][;]\n1/2;3"), "Exception not thrown");
        assertEquals(6, calculator.add("//[///][;´]\n1///2;´3"), "Exception not thrown");
        assertEquals(10, calculator.add("//[///][;´][_]\n1///2;´3_4"), "Exception not thrown");
    }
}
