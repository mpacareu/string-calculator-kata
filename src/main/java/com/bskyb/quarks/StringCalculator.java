package com.bskyb.quarks;

import java.util.regex.Pattern;

/**
 * Hello world!
 */
public class StringCalculator {
    public int add(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }

        String delimiter = "[,\n]";
        if (numbers.startsWith("//")){
            delimiter = numbers.substring("//".length(), numbers.indexOf("\n"));

            if (delimiter.startsWith("[") && delimiter.endsWith("]")){
                delimiter = delimiter.substring(1, delimiter.length() -1);
            }

            delimiter = Pattern.quote(delimiter); //Preparem Delimiter pq funcio split no peti
            numbers = numbers.substring(numbers.indexOf("\n") + 1);
        }

        String[] parts = numbers.split(delimiter);
        int sum = 0;
        String negatives = "";

        for (String part : parts) { //for (int i = 0; i < parts.length; i++) {
            int number = Integer.parseInt(part);
            if (number < 0){
                if (!negatives.isEmpty()){
                    negatives += ", ";
                }
                negatives += number;
            }
            if (number <= 1000) {
                sum += number;
            }
        }

        if (!negatives.isEmpty()){
            throw new IllegalArgumentException("negatives not allowed: " + negatives);
        }
        return sum;
    }
}
