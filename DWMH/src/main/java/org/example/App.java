package org.example;

import org.example.data.LocationJdbcTemplateRepository;
import org.example.data.ReservationJdbcTemplateRepository;
import org.example.data.UserJdbcTemplateRepository;
import org.example.domain.ReservationService;
import org.example.ui.ConsoleIO;
import org.example.ui.Controller;
import org.example.ui.TextIO;
import org.example.ui.View;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class App {
    public static void main(String[] args) {
        //Instantiate Repositories, inject JDBC Template
        ReservationJdbcTemplateRepository reservationRepository = new ReservationJdbcTemplateRepository(DataHelper.getJdbcTemplate());
        LocationJdbcTemplateRepository locationRepository = new LocationJdbcTemplateRepository(DataHelper.getJdbcTemplate());
        UserJdbcTemplateRepository userRepository = new UserJdbcTemplateRepository(DataHelper.getJdbcTemplate());

        //Instantiate Service, inject Repositories
        ReservationService reservationService = new ReservationService(reservationRepository, locationRepository, userRepository);

        //Instantiate Controller, inject view and service.
        TextIO io = new ConsoleIO();
        View view = new View(io);
        Controller controller = new Controller(view, reservationService);

        //Run Application
        controller.run();

    }
}