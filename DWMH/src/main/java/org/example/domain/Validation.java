package org.example.domain;

import java.util.regex.Pattern;


//Contains static methods which can be called without a validation object,
// used to help with validation in my service class.
public class Validation {
    public static boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }

    public static boolean isValidEmail(String emailAddress) {
        // Expression for RFC 5322
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        return Pattern.compile(regex)
                .matcher(emailAddress)
                .matches();
    }
}
