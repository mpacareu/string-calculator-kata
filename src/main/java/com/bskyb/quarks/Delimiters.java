package com.bskyb.quarks;

import java.util.ArrayList;
import java.util.List;
public class Delimiters {
    private static final String DELIMITER_STARTER = "//";
    private static final int POSITION_OF_SHORT_DELIMITER = 2;
    private static final String OPENING_SQUARE_BRACKET_BASIC = "[";
    private static final String CLOSING_SQUARE_BRACKET = "]";
    private static final String NULL_STRING = "";
    private static final String LINE_BREAK = "\n";

    List<String> listOfDelimiters;

    public Delimiters(String numbers) {
        List<String> delimitersArray = new ArrayList<>();

        if (numbers.startsWith(DELIMITER_STARTER)) {
            if (isMoreThanOneDelimiterOrLongDelimiter(numbers)) {
                String delimiters = getStringWithAllDelimiters(numbers);
                while (!delimiters.isEmpty()) {
                    delimitersArray.add(getDelimiterBetweenBrackets(delimiters));
                    delimiters = removeFirstDelimiter(delimiters);
                }
            } else {
                delimitersArray.add(getShortDelimiter(numbers));
            }
        }

        this.listOfDelimiters = delimitersArray;
    }

    public List<String> getListOfDelimiters() {
        return listOfDelimiters;
    }

    private String getStringWithAllDelimiters(String numbers) {
        return numbers.substring(0, numbers.lastIndexOf(CLOSING_SQUARE_BRACKET) + 1).replace(LINE_BREAK, NULL_STRING);
    }

    private String removeFirstDelimiter(String delimiters) {
        return delimiters.substring(delimiters.indexOf(CLOSING_SQUARE_BRACKET) + 1);
    }

    private String getDelimiterBetweenBrackets(String delimiters) {
        return delimiters.substring(delimiters.indexOf(OPENING_SQUARE_BRACKET_BASIC) + 1, delimiters.indexOf(CLOSING_SQUARE_BRACKET));
    }

    private boolean isMoreThanOneDelimiterOrLongDelimiter(String numbers) {
        return OPENING_SQUARE_BRACKET_BASIC.equals(Character.toString(numbers.charAt(POSITION_OF_SHORT_DELIMITER)))
                && (numbers.contains(CLOSING_SQUARE_BRACKET));
    }

    private String getShortDelimiter(String numbers) {
        return String.valueOf(numbers.charAt(POSITION_OF_SHORT_DELIMITER));
    }
}
