package org.example.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleIO implements TextIO {
    private final Scanner console = new Scanner(System.in);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @Override
    public void println(Object value) {
        System.out.println(value);
    }

    @Override
    public void printf(String format, Object... values) {
        System.out.printf(format, values);
    }


    //Generic Print Header Method
    @Override
    public void printHeader(String header) {
        println("");
        println(header);
        println("=".repeat(header.length()));
    }

    //Generic Read String - Null Response is Allowed.
    @Override
    public String readString(String prompt) {
        // for consistent display of prompts
        // remove any leading and trailing whitespace and add a space after the prompt
        printf("%s: ", prompt.trim());
        return console.nextLine().trim();
    }

    //Read String, Response required.
    @Override
    public String readRequiredString(String prompt) {
        while (true) {
            String value = readString(prompt);
            if (value != null && !value.isBlank()) {
                return value;
            }
            printf("[ERR] You must provide a value.%n");
        }
    }

    //Read Boolean - y or n required
    @Override
    public boolean readBoolean(String prompt) {
        while(true){
        String result = readString(prompt);
            if (result.equalsIgnoreCase("y")){
                return true;
            } else if (result.equalsIgnoreCase("n")) {
                return false;
            }
            else {
                println("[ERR] Please select 'y' or 'n'");
            }
        }
    }

    //Read Int
    @Override
    public int readInt(String prompt) {
        while (true) {
            String value = readString(prompt);
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                printf("[ERR] '%s' is not a valid number.%n", value);
            }
        }
    }

    //Read int, allows for range input
    @Override
    public int readInt(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            printf("[ERR] Value must be between %s and %s.%n", min, max);
        }
    }

    //Read double, allows for range input
    @Override
    public double readDouble(String prompt, double min, double max) {
        while (true) {
            try {
                double value = Double.parseDouble(readString(prompt));
                if (value >= min && value <= max) {
                    return value;
                } else {
                    printf("[ERR] Value must be between %s and %s.%n", min, max);
                }
            } catch (NumberFormatException ex) {
                println("[ERR] Please enter a valid decimal number.");
            }
        }
    }

    //Read Date
    @Override
    public LocalDate readLocalDate(String prompt) {
        while (true) {
            String input = readRequiredString(prompt);
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException ex) {
                println("[ERR] Please enter date in MM/DD/YYYY format.");
            }
        }
    }

    //Read Big Decimal
    @Override
    public BigDecimal readBigDecimal(String prompt) {
        while (true) {
            String input = readRequiredString(prompt);
            try {
                return new BigDecimal(input);
            } catch (NumberFormatException exception) {
                println("[ERR] Please enter a valid decimal.");
            }
        }
    }

    //Read Enum
    @Override
    public <T extends Enum<T>> T readEnum(String prompt, Class<T> tEnum) {
        printHeader(prompt);
        T[] enumConstants = tEnum.getEnumConstants();
        for (int i = 0; i < enumConstants.length; i++) {
            System.out.printf("%s. %s%n", i + 1, enumConstants[i]);
        }
        String label = String.format("Select [1-%s]", enumConstants.length);
        int index = readInt(label, 1, enumConstants.length);
        return enumConstants[index - 1];
    }

}
