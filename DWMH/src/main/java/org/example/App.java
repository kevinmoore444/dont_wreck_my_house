package org.example;

import org.example.data.ReservationJdbcTemplateRepository;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class App {
    public static void main(String[] args) {
        ReservationJdbcTemplateRepository repository = new ReservationJdbcTemplateRepository(DataHelper.getJdbcTemplate());
        
    }
}