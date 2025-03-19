package com.bskyb.quarks;

import java.util.ArrayList;
import java.util.List;
public class Delimiters {
    private static final String DELIMITER_STARTER = "//";
    private static final String LINE_BREAK = "\n";

    private static final int POSITION_OF_SHORT_DELIMITER = 2;
    private static final int THREE = 3;

    private static final String OPENING_SQUARE_BRACKET_BASIC = "[";

    List<String> listOfDelimiters;

    public Delimiters(String numbers) {
        List<String> delimiters = new ArrayList<>();

        if (numbers.startsWith(DELIMITER_STARTER)) {
            if (isMoreThanOneDelimiterOrLongDelimiter(numbers)) {
                String delimitersPart = getStringWithAllDelimiters(numbers);
                while (!delimitersPart.isEmpty()) {
                    delimiters.add(getDelimiterBetweenBrackets(delimitersPart));
                    delimitersPart = removeFirstDelimiter(delimitersPart);
                }
            } else {
                delimiters.add(getShortDelimiter(numbers));
            }
        }

        this.listOfDelimiters = delimiters;
    }

    private String getStringWithAllDelimiters(String numbers) {
        return numbers.substring(0, numbers.lastIndexOf("]") + 1);
    }

    private String removeFirstDelimiter(String delimitersPart) {
        return delimitersPart.substring(delimitersPart.indexOf("]") + 1);
    }

    private String getDelimiterBetweenBrackets(String delimitersPart) {
        return delimitersPart.substring(delimitersPart.indexOf("[") + 1, delimitersPart.indexOf("]"));
    }

    private boolean isMoreThanOneDelimiterOrLongDelimiter(String numbers) {
        return OPENING_SQUARE_BRACKET_BASIC.equals(Character.toString(numbers.charAt(POSITION_OF_SHORT_DELIMITER))) && !(LINE_BREAK.equals(Character.toString(numbers.charAt(THREE))));
    }

    private String getShortDelimiter(String numbers) {
        return String.valueOf(numbers.charAt(POSITION_OF_SHORT_DELIMITER));
    }

    public List<String> getListOfDelimiters() {
        return listOfDelimiters;
    }
}
