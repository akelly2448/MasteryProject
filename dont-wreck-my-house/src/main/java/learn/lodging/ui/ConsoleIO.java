package learn.lodging.ui;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    public LocalDate readLocalDate(String prompt, LocalDate current){
        while (true){
            String input = readString(prompt);
            if (input.isBlank()){
                return current;
            }
            try{
                return LocalDate.parse(input,formatter);
            } catch (DateTimeParseException ex){
                println(INVALID_DATE);
            }

        }
    }

    public LocalDate readLocalDate(String prompt){
        while(true){
            String input = readRequiredString(prompt);
            try{
                return LocalDate.parse(input,formatter);
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
}
