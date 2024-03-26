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

    JdbcTemplate jdbcTemplate = DataHelper.getJdbcTemplate();

    ReservationJdbcTemplateRepository repository = new ReservationJdbcTemplateRepository(jdbcTemplate);

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
        Reservation newReservation = new Reservation();
        newReservation.setLocation(new Location(1, new User(1)));
        newReservation.setGuest(new User(2));
        newReservation.setStartDate(LocalDate.now());
        newReservation.setEndDate(LocalDate.now().plusDays(3));
        newReservation.setTotal(new BigDecimal("400.00"));

        Reservation createdReservation = repository.add(newReservation);

        assertEquals(createdReservation.getTotal(), new BigDecimal("400.00"));

    }

    //Update Reservation
    @Test
    void shouldUpdate(){
        Reservation reservationUpdate = repository.findByID(1);
        reservationUpdate.setStartDate(LocalDate.now());
        reservationUpdate.setEndDate(LocalDate.now().plusDays(3));
        reservationUpdate.setTotal(new BigDecimal("600.00"));
        assertTrue(repository.update(reservationUpdate));
        Reservation updatedReservation = repository.findByID(1);
        assertEquals(updatedReservation.getTotal(),new BigDecimal("600.00"));

    }
    //Delete Reservation
    @Test
    void shouldDelete(){
        assertTrue(repository.deleteById(2));
    }

    //Find By ID
    @Test
    void shouldFindById(){
        Reservation reservation = repository.findByID(1);
        assertEquals(reservation.getTotal(), new BigDecimal("300.00"));
        assertEquals(reservation.getLocation().getLocationId(), 1);
    }



}
