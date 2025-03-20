package com.bskyb.quarks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringCalculatorTest {

    private static final String NOT_RETURNING_THE_CORRECT_NUMBER = "Not returning the correct number";

    @Test
    void add_WhenEmpty_ThenReturnsZero() {
        StringCalculator calculator = new StringCalculator();

        assertEquals(0, calculator.add(""), "Not returning 0 if empty string");
    }

    @Test
    void add_WhenNumbersSeparedByComa() {
        StringCalculator calculator = new StringCalculator();

        assertEquals(4, calculator.add("4"), NOT_RETURNING_THE_CORRECT_NUMBER);
        assertEquals(4, calculator.add("3,,1"), NOT_RETURNING_THE_CORRECT_NUMBER);
        assertEquals(119, calculator.add("20,45,54"), NOT_RETURNING_THE_CORRECT_NUMBER);
    }

    @Test
    void add_WhenNumbersSeparatedByNewLines() {
        StringCalculator calculator = new StringCalculator();

        assertEquals(6, calculator.add("1\n2,3"), NOT_RETURNING_THE_CORRECT_NUMBER);
        assertEquals(6, calculator.add("1\n2,\n\n3"), NOT_RETURNING_THE_CORRECT_NUMBER);
    }

    @Test
    void add_WhenDifferentDelimiters() {
        StringCalculator calculator = new StringCalculator();

        assertEquals(3, calculator.add("//;\n1;2"), NOT_RETURNING_THE_CORRECT_NUMBER);
        assertEquals(3, calculator.add("// \n1 2"), NOT_RETURNING_THE_CORRECT_NUMBER);
        assertEquals(3, calculator.add("///\n1/2"), NOT_RETURNING_THE_CORRECT_NUMBER);
        assertEquals(3, calculator.add("//}\n1{2"), NOT_RETURNING_THE_CORRECT_NUMBER);
        assertEquals(3, calculator.add("//]\n1]2"), NOT_RETURNING_THE_CORRECT_NUMBER);
    }

    private void assertNegativeNumbers(String input, String expectedMessage) {
        StringCalculator calculator = new StringCalculator();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.add(input)
        );
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void add_WhenNumberIsNegative_ThenThrowExceptionWithSpecificMessages() {

        assertNegativeNumbers("-1", "Negative numbers are not supported: [-1]");
        assertNegativeNumbers("-1,-4,7,-8", "Negative numbers are not supported: [-1, -4, -8]");
    }

    @Test
    void add_WhenNumbersBiggerThan1000_ThenIgnoreThem() {
        StringCalculator calculator = new StringCalculator();

        assertEquals(2, calculator.add("1001,2"), "Exception not thrown");
        assertEquals(1006, calculator.add("1000,2,5,999"), "Exception not thrown");
    }

    @Test
    void add_WhenDelimiterItsMoreTHanOneCharacter() {
        StringCalculator calculator = new StringCalculator();

        assertEquals(6, calculator.add("//[´´´]\n1´´´2´´´3"), NOT_RETURNING_THE_CORRECT_NUMBER);
        assertEquals(6, calculator.add("//[***]\n1***2***3"), NOT_RETURNING_THE_CORRECT_NUMBER);
    }

    @Test
    void add_WhenMoreThanOneDelimiter() {
        StringCalculator calculator = new StringCalculator();

        assertEquals(6, calculator.add("//[/][;]\n1/2;3"), NOT_RETURNING_THE_CORRECT_NUMBER);
        assertEquals(6, calculator.add("//[///][;´]\n1///2;´3"), NOT_RETURNING_THE_CORRECT_NUMBER);
        assertEquals(10, calculator.add("//[///][;´][_]\n1///2;´3_4"), NOT_RETURNING_THE_CORRECT_NUMBER);
        assertEquals(6, calculator.add("//[abc][;]\n1abc2;3"), NOT_RETURNING_THE_CORRECT_NUMBER);

    }

    @Test
    void add_WhenNames_ThenIgnore(){
        StringCalculator calculator = new StringCalculator();

        assertEquals(4, calculator.add("//[/][;]\n1/abc;3"), NOT_RETURNING_THE_CORRECT_NUMBER);
        assertEquals(4, calculator.add("//[/][;]\n1/abcdf;3"), NOT_RETURNING_THE_CORRECT_NUMBER);
        assertEquals(4, calculator.add("//[/][;]\n1/3b4f;3"), NOT_RETURNING_THE_CORRECT_NUMBER);
    }
    //EDGE CASES
    //adding names
    //adding characters
    //Delimiters [
    //Delimiters \n

}
