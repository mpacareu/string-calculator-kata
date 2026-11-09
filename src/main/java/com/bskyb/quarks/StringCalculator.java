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

        for (String part : parts) { //for (int i = 0; i < parts.length; i++) {
            sum += Integer.parseInt(part);
        }
        return sum;
    }
}
