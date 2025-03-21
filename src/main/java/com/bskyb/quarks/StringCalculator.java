package com.bskyb.quarks;

import java.util.ArrayList;
import java.util.List;

public class StringCalculator {

    private static final String COMA = ",";
    private static final String NULL_STRING = "";
    private static final String LINE_BREAK = "\n";

    private static final String OPENING_SQUARE_BRACKET = "\\[";
    private static final String CLOSING_SQUARE_BRACKET = String.valueOf(']');
    private static final String OPENING_CURLY_BRACES = "{";
    private static final String CLOSING_CURLY_BRACES = "}";
    private static final String CLOSING_BRACKET = "\\)";
    private static final String OPENING_BRACKET = "\\(";

    private static final String ASTERISK = "\\*";
    private static final String SUM = "\\+";

    private static final String NEGATIVE_NUMBERS_ARE_NOT_SUPPORTED = "Negative numbers are not supported";
    private static final int SIZE_OF_NUMBERS_SMALLER_THAN_1000 = 3;

    public Integer add(String numbers) {
        List<Integer> negatives = new ArrayList<>();
        numbers = dealWithMetacharacters(numbers);

        Delimiters delimiters = new Delimiters(numbers);

        String[] numbersArray = getArrayOfNumbersSeparated(numbers, delimiters.getListOfDelimiters());

        int sum = 0;
        for (String number : numbersArray) {
            if (isNumberAndLowerThan1000(number)) {
                int parsedNumber = Integer.parseInt(number);
                if (isNegative(parsedNumber)) {
                    negatives.add(parsedNumber);
                } else {
                    sum += parsedNumber;
                }
            }
        }
        checkIfNegativesAndThrowException(negatives);
        return sum;
    }

    private void checkIfNegativesAndThrowException(List<Integer> negatives) {
        if (!negatives.isEmpty()) {
            throw new IllegalArgumentException(NEGATIVE_NUMBERS_ARE_NOT_SUPPORTED + negatives);
        }
    }

    private String[] getArrayOfNumbersSeparated(String numbers, List<String> delimiters) {
        if (!delimiters.isEmpty()) {
            numbers = numbers.substring(numbers.indexOf(LINE_BREAK) + 1);
        }
        numbers = dealWithUnclosedCharacter(numbers.replace(LINE_BREAK, COMA));
        for (String delimiter : delimiters) {
            numbers = numbers.replace(delimiter, COMA);
        }
        return numbers.split(COMA);
    }

    private String dealWithUnclosedCharacter(String numbers) {
        numbers = numbers.replace(CLOSING_BRACKET, NULL_STRING).replace(OPENING_BRACKET, NULL_STRING).replace(OPENING_CURLY_BRACES, COMA).replace(CLOSING_CURLY_BRACES, COMA).replace(CLOSING_SQUARE_BRACKET, COMA).replace(OPENING_SQUARE_BRACKET, COMA);
        return numbers;
    }

    private String dealWithMetacharacters(String numbers) {
        return numbers.replace(ASTERISK, COMA).replace(SUM, COMA);
    }

    private boolean isNegative(int parsedNumber) {
        return parsedNumber < 0;
    }

    private boolean isNumberAndLowerThan1000(String number) {
        return !(number.length() > SIZE_OF_NUMBERS_SMALLER_THAN_1000 || !isNumeric(number));
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
