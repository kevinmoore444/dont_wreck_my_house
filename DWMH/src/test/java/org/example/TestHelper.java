package org.example;

import org.example.domain.Result;
import org.example.models.Location;
import org.example.models.Reservation;
import org.example.models.User;

import java.math.BigDecimal;
import java.time.LocalDate;


public class TestHelper {

    //I'm creating an id parameter for my make methods which will allow me to customize my objects
    //for testing purposes.
    public static Reservation makeReservation(int reservationID){
        Reservation reservation = new Reservation();
        //Two valid reservations. I need two different ones so that I can pass the overlapping dates validation.
        if(reservationID == 1) {
            reservation.setReservationId(1);
            reservation.setGuest(makeUser(1));
            reservation.setLocation(makeLocation(1));
            reservation.setStartDate(LocalDate.of(2030, 12, 24));
            reservation.setEndDate(LocalDate.of(2030, 12, 30));
            reservation.setTotal(BigDecimal.valueOf(100.00));
        }
        if(reservationID == 2) {
            reservation.setReservationId(2);
            reservation.setGuest(makeUser(1));
            reservation.setLocation(makeLocation(1));
            reservation.setStartDate(LocalDate.of(2025, 12, 24));
            reservation.setEndDate(LocalDate.of(2025, 12, 30));
            reservation.setTotal(BigDecimal.valueOf(100.00));
        }
        return reservation;
    }

    public static User makeUser(int userId){
        User user = new User();
        //Valid Users - one to be a host, one to be a guest.
        if (userId == 1){
            user.setUserId(1);
            user.setFirst_name("Llywellyn");
            user.setLast_name("Vondra");
            user.setEmail("lvondra0@vkontakte.ru");
            user.setPhone("(940) 3612277");
        }
        if (userId == 2){
            user.setUserId(2);
            user.setFirst_name("Roanna");
            user.setLast_name("Klimpt");
            user.setEmail("rklimpt1@paginegialle.it");
            user.setPhone("(277) 2824355");
        }
        return user;
    }

    public static Location makeLocation(int locationId){
        Location location = new Location();
        //Valid Location
        if(locationId == 1){
            location.setLocationId(1);
            location.setHost(makeUser(2));
            location.setAddress("6497 Manitowish Circle");
            location.setCity("Melbourne");
            location.setState("FL");
            location.setPostalCode("32919");
            location.setStandardRate(BigDecimal.valueOf(110.00));
            location.setWeekendRate(BigDecimal.valueOf(110.00));
        }
        return location;
    }




    public static <T> Result<T> makeResult(String message, T payload) {
        Result<T> result = new Result<>();
        if (message != null) {
            result.addErrorMessage(message);
        }
        if (payload != null) {
            result.setPayload(payload);
        }
        return result;
    }
}
