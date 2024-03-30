package org.example.ui;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TextIO {
    void println(Object value);

    void printf(String format, Object... values);

    void printHeader(String header);

    String readString(String prompt);

    String readRequiredString(String prompt);

    boolean readBoolean(String prompt);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    double readDouble(String prompt, double min, double max);

    LocalDate readLocalDate(String prompt);

    BigDecimal readBigDecimal(String prompt);

    <T extends Enum<T>> T readEnum(String prompt, Class<T> tEnum);
}