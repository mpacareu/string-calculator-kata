package com.bskyb.quarks;

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
            numbers = numbers.substring(numbers.indexOf("\n") + 1); //Considero que el Delimiter té sempre llargada = 1
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
            sum += number;
        }

        if (!negatives.isEmpty()){
            throw new IllegalArgumentException("negatives not allowed: " + negatives);
        }
        return sum;
    }
}
