package com.bskyb.quarks;

import java.util.ArrayList;
import java.util.List;

public class StringCalculator {

    public static final String DELIMITER_STARTER = "//";
    public static final String COMA = ",";
    public static final String NULL_STRING = "";
    public static final String LINE_BREAK = "\n";

    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;

    public static final String OPENING_SQUARE_BRACKET = "\\[";
    public static final String OPENING_SQUARE_BRAKET_BASIC = "[";
    public static final String CLOSING_SQUARE_BRACKET = String.valueOf(']');
    public static final String OPENING_CURLY_BRACES = "{";
    public static final String CLOSING_CURLY_BRACES = "}";
    public static final String CLOSING_BRAKET = "\\)";
    public static final String OPENING_BRAKET = "\\(";

    public static final String ASTERISC = "\\*";
    public static final String SUM = "\\+";

    public static final String NEGATIVE_NUMBERS_ARE_NOT_SUPPORTED = "Negative numbers are not supported";

    public Integer add(String numbers) {
        List<Integer> negatives = new ArrayList<>();
        List<String> delimiters = new ArrayList<>();

        //should substitute this for something taking care of all special characters
        numbers = dealWithMetacharacters(numbers);
        if (numbers.startsWith(DELIMITER_STARTER)) {
            if (isMoreThanOneDelimiterOrLongDelimiter(numbers)) {
                numbers = numbers.replaceFirst(DELIMITER_STARTER, NULL_STRING);
                while (numbers.startsWith(OPENING_SQUARE_BRAKET_BASIC)) {
                    delimiters.add(getLongDelimiter(numbers));
                    numbers = numbers.replaceFirst(OPENING_SQUARE_BRACKET + delimiters.get(delimiters.size() - ONE) + CLOSING_SQUARE_BRACKET, NULL_STRING);
                }
            } else {
                delimiters.add(getShortDelimiter(numbers));
                numbers = numbers.replaceFirst(DELIMITER_STARTER + delimiters, NULL_STRING);
            }
        }

        String[] numbersArray = getArrayOfNumbersSepared(numbers, delimiters);

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

    private static String[] getArrayOfNumbersSepared(String numbers, List<String> delimiters) {
        numbers = dealWithUnclosedCharacter(numbers.replace(LINE_BREAK, COMA));
        for (String delimiter : delimiters) {
            numbers = numbers.replace(delimiter,COMA);
        }
        return numbers.split(COMA);
    }

    private static String dealWithUnclosedCharacter(String numbers) {
        if(numbers.contains(CLOSING_BRAKET)||numbers.contains(OPENING_BRAKET)||numbers.contains(OPENING_CURLY_BRACES)|| numbers.contains(CLOSING_CURLY_BRACES) || numbers.contains(CLOSING_SQUARE_BRACKET) || numbers.contains(OPENING_SQUARE_BRACKET)){
            numbers = numbers.replace(CLOSING_BRAKET,NULL_STRING).replace(OPENING_BRAKET,NULL_STRING).replace(OPENING_CURLY_BRACES,COMA).replace(CLOSING_CURLY_BRACES,COMA).replace(CLOSING_SQUARE_BRACKET,COMA).replace(OPENING_SQUARE_BRACKET,COMA);
        }
        return numbers;
    }

    private String dealWithMetacharacters(String numbers) {
        return numbers.replaceAll(ASTERISC, COMA).replaceAll(SUM, COMA);
    }

    private static boolean isNegative(int parsedNumber) {
        return parsedNumber < ZERO;
    }

    private static boolean isNumberLowerThan1000(String number) {
        return !(NULL_STRING.equals(number) || number.length() > THREE);
    }

    private static boolean isMoreThanOneDelimiterOrLongDelimiter(String numbers) {
        return OPENING_SQUARE_BRAKET_BASIC.equals(Character.toString(numbers.charAt(TWO))) && !(LINE_BREAK.equals(Character.toString(numbers.charAt(THREE))));
    }

    private static String getLongDelimiter(String numbers) {
        String delimiter;
        String[] numbersArrays = numbers.split(CLOSING_SQUARE_BRACKET);
        delimiter = numbersArrays[ZERO].replaceFirst(OPENING_SQUARE_BRACKET, NULL_STRING).replace(CLOSING_SQUARE_BRACKET, NULL_STRING);
        return delimiter;
    }

    private static String getShortDelimiter(String numbers) {
        return String.valueOf(numbers.charAt(TWO));
    }
}
