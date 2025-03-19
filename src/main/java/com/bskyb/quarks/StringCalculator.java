package com.bskyb.quarks;

import java.util.ArrayList;
import java.util.List;

public class StringCalculator {

    private static final String DELIMITER_STARTER = "//";
    private static final String COMA = ",";
    private static final String NULL_STRING = "";
    private static final String LINE_BREAK = "\n";

    private static final int ZERO = 0;
    private static final int POSITION_OF_SHORT_DELIMITER = 2;
    private static final int THREE = 3;

    private static final String OPENING_SQUARE_BRACKET = "\\[";
    private static final String OPENING_SQUARE_BRACKET_BASIC = "[";
    private static final String CLOSING_SQUARE_BRACKET = String.valueOf(']');
    private static final String OPENING_CURLY_BRACES = "{";
    private static final String CLOSING_CURLY_BRACES = "}";
    private static final String CLOSING_BRACKET = "\\)";
    private static final String OPENING_BRACKET = "\\(";

    private static final String ASTERISK = "\\*";
    private static final String SUM = "\\+";

    private static final String NEGATIVE_NUMBERS_ARE_NOT_SUPPORTED = "Negative numbers are not supported";

    public Integer add(String numbers) {
        List<Integer> negatives = new ArrayList<>();
        numbers = dealWithMetacharacters(numbers);

        List<String> delimiters = getDelimitersInArray(numbers);

        String[] numbersArray = getArrayOfNumbersSeparated(numbers, delimiters);

        int sum = 0;
        for (String number : numbersArray) {
            if (isNumberLowerThan1000(number)) {
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

    private static void checkIfNegativesAndThrowException(List<Integer> negatives) {
        if (!negatives.isEmpty()) {
            throw new IllegalArgumentException(NEGATIVE_NUMBERS_ARE_NOT_SUPPORTED + ": " + negatives);
        }
    }

    private List<String> getDelimitersInArray(String numbers) {
        List<String> delimiters = new ArrayList<>();

        if (numbers.startsWith(DELIMITER_STARTER)) {
            if (isMoreThanOneDelimiterOrLongDelimiter(numbers)) {
                String delimiters_string = getStringWithAllDelimiters(numbers);
                while (!delimiters_string.isEmpty()) {
                    delimiters.add(getDelimiterBetweenBrackets(delimiters_string));
                    delimiters_string = removeFirstDelimiter(delimiters_string);
                }
            } else {
                delimiters.add(getShortDelimiter(numbers));
            }
        }
        return delimiters;
    }

    private static String getStringWithAllDelimiters(String numbers) {
        return numbers.substring(0, numbers.lastIndexOf("]") + 1);
    }

    private static String removeFirstDelimiter(String delimiters_string) {
        return delimiters_string.substring(delimiters_string.indexOf("]") + 1);
    }

    private static String getDelimiterBetweenBrackets(String delimiters_string) {
        return delimiters_string.substring(delimiters_string.indexOf("[") + 1, delimiters_string.indexOf("]"));
    }

    private String[] getArrayOfNumbersSeparated(String numbers, List<String> delimiters) {
        if (!delimiters.isEmpty()) {
            numbers = numbers.substring(numbers.indexOf("\n") + 1);
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
        return numbers.replaceAll(ASTERISK, COMA).replaceAll(SUM, COMA);
    }

    private boolean isNegative(int parsedNumber) {
        return parsedNumber < ZERO;
    }

    private boolean isNumberLowerThan1000(String number) {
        return !(NULL_STRING.equals(number) || number.length() > THREE);
    }

    private boolean isMoreThanOneDelimiterOrLongDelimiter(String numbers) {
        return OPENING_SQUARE_BRACKET_BASIC.equals(Character.toString(numbers.charAt(POSITION_OF_SHORT_DELIMITER))) && !(LINE_BREAK.equals(Character.toString(numbers.charAt(THREE))));
    }

    private String getShortDelimiter(String numbers) {
        return String.valueOf(numbers.charAt(POSITION_OF_SHORT_DELIMITER));
    }
}
