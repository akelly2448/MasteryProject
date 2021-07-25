package learn.lodging.ui;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@Component
public class ConsoleIO {

    private static final String REQUIRED
            = "[INVALID] Input is required.";
    private static final String INVALID_DATE
            = "[INVALID] Enter a date in MM/dd/yyyy format.";
    private static final String IVALID_CONFIRMATION
            = "[INVALID] Please enter 'y' or 'n'.";
    private static final String INVALID_NUMBER
            = "[INVALID] Enter a valid number.";
    private static final String NUMBER_OUT_OF_RANGE
            = "[INVALID] Enter a number between %s and %s.";
    private final ArrayList<String> ALL_STATES_ACRONYMS = new ArrayList<>(List.of("AK", "AL", "AR", "AS", "AZ", "CA", "CO",
            "CT", "DC", "DE", "FL", "GA", "GU", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME",
            "MI", "MN", "MO", "MP", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR",
            "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UM", "UT", "VA", "VI", "VT", "WA", "WI", "WV", "WY"));


    private final Scanner console = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public void print(String message){
        System.out.print(message);
    }

    public void println(String message){
        System.out.println(message);
    }

    public void printf(String format, Object... values){
        System.out.printf(format,values);
    }

    public String readString(String prompt, String current){
        print(prompt);
        String input = console.nextLine();
        if (input.isBlank()){
            return current;
        }else{
            return input;
        }
    }
    public String readString(String prompt){
        print(prompt);
        return console.nextLine();
    }
    public String readRequiredString(String prompt){
        while (true){
            String input = readString(prompt);
            if (!input.isBlank()){
                return input;
            }
            println(REQUIRED);
        }
    }
    public int readInt(String prompt){
        while(true){
            try {
                return Integer.parseInt(readRequiredString(prompt));
            } catch (NumberFormatException ex){
                println(INVALID_NUMBER);
            }
        }
    }
    public int readInt(String prompt, int min, int max){
        while (true){
            int result = readInt(prompt);
            if (result >= min && result <= max){
                return result;
            }
            println(String.format(NUMBER_OUT_OF_RANGE,min,max));
        }
    }

    public String readEmail(String prompt){
        while(true){
            String input = readRequiredString(prompt);
            if (!(input.contains("@") && input.contains("."))){
                println("Email must be a valid email address (containing '@' and '.').");
            }else {
                return input;
            }
        }
    }

    public String readEmail(String prompt, String current){
        while (true){
            String input = readString(prompt);
            if (input.isBlank()){
                return current;
            }else{
                if (!(input.contains("@") && input.contains("."))){
                    println("Email must be a valid email address (containing '@' and '.').");
                }else {
                    return input;
                }
            }
        }
    }

    public String readPhoneNum(String prompt){
        while (true){
            String input = readRequiredString(prompt);
            boolean isValidNum = true;
            for (int i = 0; i < input.length(); i++){
                if (!Character.isDigit(input.charAt(i))){
                    isValidNum = false;
                }
            }
            if (!isValidNum || input.length() != 10){
                println("Phone # must a valid phone #.");
            }else{
                return input;
            }
        }
    }

    public String readPhoneNum(String prompt, String current){
        while (true){
            String input = readString(prompt);
            if (input.isBlank()){
                return current;
            }else{
                boolean isValidNum = true;
                for (int i = 0; i < input.length(); i++){
                    if (!Character.isDigit(input.charAt(i))){
                        isValidNum = false;
                    }
                }
                if (!isValidNum || input.length() != 10){
                    println("Phone # must a valid phone #.");
                }else{
                    return input;
                }
            }
        }
    }

    public String readState(String prompt){
        while (true){
            String input = readRequiredString(prompt);
            if (!ALL_STATES_ACRONYMS.contains(input)){
                println("State must be a valid US state acronym.");
            }else{
                return input;
            }
        }
    }

    public String readState(String prompt, String current){
        while(true){
            String input = readString(prompt);
            if (input.isBlank()){
                return current;
            }else {
                if (!ALL_STATES_ACRONYMS.contains(input)){
                    println("State must be a valid US state acronym.");
                }else{
                    return input;
                }
            }
        }
    }

    public String readPostal(String prompt){
        while (true){
            String input = readRequiredString(prompt);
            boolean isValidPostal = true;
            for (int i = 0; i < input.length(); i++){
                if (!Character.isDigit(input.charAt(i))){
                    isValidPostal = false;
                }
            }
            if (!isValidPostal || input.length() != 5){
                println("Postal Code must be valid (5 integers).");
            }else{
                return input;
            }
        }
    }

    public String readPostal(String prompt, String current){
        while (true){
            String input = readString(prompt);
            if (input.isBlank()){
                return current;
            }else{
                boolean isValidPostal = true;
                for (int i = 0; i < input.length(); i++){
                    if (!Character.isDigit(input.charAt(i))){
                        isValidPostal = false;
                    }
                }
                if (!isValidPostal || input.length() != 5){
                    println("Postal Code must be valid (5 integers).");
                }else{
                    return input;
                }
            }
        }
    }

    public LocalDate readLocalDate(String prompt, LocalDate current){
        while (true){
            String input = readString(prompt);
            if (input.isBlank()){
                return current;
            }
            try{
                LocalDate date = LocalDate.parse(input,formatter);
                if (date.isBefore(LocalDate.now())){
                    println("Date must be in the future.");
                }else{
                    return date;
                }
            } catch (DateTimeParseException ex){
                println(INVALID_DATE);
            }

        }
    }

    public LocalDate readLocalDate(String prompt){
        while(true){
            String input = readRequiredString(prompt);
            try{
                LocalDate date = LocalDate.parse(input,formatter);
                if (date.isBefore(LocalDate.now())){
                    println("Date must be in the future.");
                }else{
                    return date;
                }
            } catch (DateTimeParseException ex){
                println(INVALID_DATE);
            }
        }
    }

    public boolean readBoolean(String prompt){
        while(true){
            String input = readRequiredString(prompt).toLowerCase(Locale.ROOT);
            if (input.equals("y")){
                return true;
            }else if (input.equals("n")){
                return false;
            }
            println(IVALID_CONFIRMATION);
        }
    }

    public BigDecimal readBigDecimal(String prompt, BigDecimal current){
        String rate = readString(prompt);
        if (rate.isBlank()){
            return current;
        }else{
            while(true){
                try {
                    return new BigDecimal(rate).setScale(2, RoundingMode.UP);
                } catch (NumberFormatException ex){
                    println(INVALID_NUMBER);
                }
            }
        }
    }

    public BigDecimal readBigDecimal(String prompt){
        while(true){
            String rate = readRequiredString(prompt);
            try {
                return new BigDecimal(rate).setScale(2, RoundingMode.UP);
            } catch (NumberFormatException ex){
                println(INVALID_NUMBER);
            }
        }
    }
}
