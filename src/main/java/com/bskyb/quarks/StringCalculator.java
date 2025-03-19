package com.bskyb.quarks;

import java.util.ArrayList;
import java.util.List;

public class StringCalculator {

    private static final String DELIMITER_STARTER = "//";
    private static final String COMA = ",";
    private static final String NULL_STRING = "";
    private static final String LINE_BREAK = "\n";

    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
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
        List<String> delimiters = new ArrayList<>();

        //should substitute this for something taking care of all special characters
        numbers = dealWithMetacharacters(numbers);
        if (numbers.startsWith(DELIMITER_STARTER)) {
            if (isMoreThanOneDelimiterOrLongDelimiter(numbers)) {
                numbers = numbers.replaceFirst(DELIMITER_STARTER, NULL_STRING);
                while (numbers.startsWith(OPENING_SQUARE_BRACKET_BASIC)) {
                    delimiters.add(getLongDelimiter(numbers));
                    numbers = numbers.replaceFirst(OPENING_SQUARE_BRACKET + delimiters.get(delimiters.size() - ONE) + CLOSING_SQUARE_BRACKET, NULL_STRING);
                }
            } else {
                delimiters.add(getShortDelimiter(numbers));
                numbers = numbers.replaceFirst(DELIMITER_STARTER + delimiters, NULL_STRING);
            }
        }

        String[] numbersArray = getArrayOfNumbersSeparated(numbers, delimiters);

        int sum = ZERO;
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
        if (!negatives.isEmpty()) {
            throw new IllegalArgumentException(NEGATIVE_NUMBERS_ARE_NOT_SUPPORTED);
        }
        return sum;
    }

    private String[] getArrayOfNumbersSeparated(String numbers, List<String> delimiters) {
        numbers = dealWithUnclosedCharacter(numbers.replace(LINE_BREAK, COMA));
        for (String delimiter : delimiters) {
            numbers = numbers.replace(delimiter,COMA);
        }
        return numbers.split(COMA);
    }

    private String dealWithUnclosedCharacter(String numbers) {
        if(numbers.contains(CLOSING_BRACKET)||numbers.contains(OPENING_BRACKET)||numbers.contains(OPENING_CURLY_BRACES)|| numbers.contains(CLOSING_CURLY_BRACES) || numbers.contains(CLOSING_SQUARE_BRACKET) || numbers.contains(OPENING_SQUARE_BRACKET)){
            numbers = numbers.replace(CLOSING_BRACKET,NULL_STRING).replace(OPENING_BRACKET,NULL_STRING).replace(OPENING_CURLY_BRACES,COMA).replace(CLOSING_CURLY_BRACES,COMA).replace(CLOSING_SQUARE_BRACKET,COMA).replace(OPENING_SQUARE_BRACKET,COMA);
        }
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
        return OPENING_SQUARE_BRACKET_BASIC.equals(Character.toString(numbers.charAt(TWO))) && !(LINE_BREAK.equals(Character.toString(numbers.charAt(THREE))));
    }

    private String getLongDelimiter(String numbers) {
        String delimiter;
        String[] numbersArrays = numbers.split(CLOSING_SQUARE_BRACKET);
        delimiter = numbersArrays[ZERO].replaceFirst(OPENING_SQUARE_BRACKET, NULL_STRING).replace(CLOSING_SQUARE_BRACKET, NULL_STRING);
        return delimiter;
    }

    private String getShortDelimiter(String numbers) {
        return String.valueOf(numbers.charAt(TWO));
    }
}
