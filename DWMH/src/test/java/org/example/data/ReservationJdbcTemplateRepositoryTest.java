package org.example.data;

import org.example.DataHelper;
import org.example.data.ReservationJdbcTemplateRepository;
import org.example.models.Location;
import org.example.models.Reservation;
import org.example.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationJdbcTemplateRepositoryTest {
    //JDBC template contains a datasource object, which contains the login info and database url for mysql.
    //The database url, stored in intelliJ edit Configurations -> J-Unit - is what tells the repo to query the
    //test database.
    JdbcTemplate jdbcTemplate = DataHelper.getJdbcTemplate();
    //Construct an instance of the actual repo, with which we'll use to conduct tests on the test database.
    ReservationJdbcTemplateRepository repository = new ReservationJdbcTemplateRepository(jdbcTemplate);

    //This is a query stored in mysql workbench DWMH_Test database.
    // It resets the database before each test to a known state with some generic data for me to
    //test and query against.
    @BeforeEach
    void setup() {
        jdbcTemplate.execute("call set_known_good_state();");
    }

    //Find By Email Address
    @Test
    void shouldFindByEmail(){
        List<Reservation> reservations = repository.findReservationsByEmailAddress("user1@gmail.com");
        assertEquals(reservations.size(), 2);
        assertEquals(reservations.get(0).getReservationId(), 1);
        assertEquals(reservations.get(1).getReservationId(), 2);
    }

    //Add Reservation
    @Test
    void shouldAdd(){
        //Built a reservation object
        Reservation newReservation = new Reservation();
        newReservation.setLocation(new Location(1, new User(1)));
        newReservation.setGuest(new User(2));
        newReservation.setStartDate(LocalDate.now());
        newReservation.setEndDate(LocalDate.now().plusDays(3));
        newReservation.setTotal(new BigDecimal("400.00"));
        //Call to add object will return the created object/
        Reservation createdReservation = repository.add(newReservation);
        //Is the createdReservation, returned from sql, equal to the new Reservation?
        assertEquals(createdReservation, newReservation);

    }

    //Update Reservation
    @Test
    void shouldUpdate(){
        //Create a reservation object which will update the one stored in the database of the same id.
        Reservation reservationUpdate = repository.findByID(1);
        reservationUpdate.setStartDate(LocalDate.now());
        reservationUpdate.setEndDate(LocalDate.now().plusDays(3));
        reservationUpdate.setTotal(new BigDecimal("600.00"));
        //Perform update. Returns true if successful.
        assertTrue(repository.update(reservationUpdate));
        //Find the updated reservation - confirm if it matches the reservation update which was passed to sql.
        Reservation updatedReservation = repository.findByID(1);
        assertEquals(reservationUpdate, updatedReservation);
    }
    //Delete Reservation Test
    @Test
    void shouldDelete(){
        assertTrue(repository.deleteById(2));
    }

    //Find By ID Test
    @Test
    void shouldFindById(){
        Reservation reservation = repository.findByID(1);
        assertEquals(reservation.getTotal(), new BigDecimal("300.00"));
        assertEquals(reservation.getLocation().getLocationId(), 1);
    }



}
