package com.bskyb.quarks;

/**
 * Hello world!
 */
public class StringCalculator {
    public int add(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }
        String[] parts = numbers.split("[,\n]");
        int sum = 0;

        for (String part : parts) { //for (int i = 0; i < parts.length; i++) {
            sum += Integer.parseInt(part);
        }
        return sum;
    }
}
