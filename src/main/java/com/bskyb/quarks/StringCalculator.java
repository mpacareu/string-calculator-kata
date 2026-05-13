package com.bskyb.quarks;

/**
 * Hello world!
 */
public class StringCalculator {

    public int add(String numbers) {
        int result = 0;
        String delimiters = "[,\\n]";
        String stringNumbers = numbers;

        boolean exception = false;
        StringBuilder exceptionMessage = new StringBuilder("negatives not allowed:");

        if(!numbers.isEmpty()){
            //Assignation of delimiters
            if(numbers.startsWith("//")){
                int index = numbers.indexOf("\n");
                var tempDelimiters = numbers.substring(2, index).split(" ");
                if(tempDelimiters.length>1){
                    StringBuilder temp = new StringBuilder();
                    for(String delimiter : tempDelimiters){
                        int indexDelimiter = delimiter.indexOf("]");
                        temp.append(delimiter, 1, indexDelimiter).append("\\");
                    }
                    temp.insert(0, "[");
                    temp.setCharAt(temp.length()-1, ']');
                    delimiters = temp.toString();
                }else{
                    delimiters = tempDelimiters[0];
                }
                stringNumbers = stringNumbers.replaceFirst("//.+\\n", "");
            }

            //Read list of numbers and calculate
            var listNum = stringNumbers.split(delimiters);
            for(String num : listNum){
                if(!num.isEmpty()){
                    var numero = Integer.parseInt(num);
                    if(numero < 0){
                        exception = true;
                        exceptionMessage.append(" ").append(numero);
                    }else{
                        if(numero <= 1000) result += numero;
                    }
                }
            }
            if(exception){
                throw new IllegalArgumentException(exceptionMessage.toString());
            }
        }
        return result;
    }
}
