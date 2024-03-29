package org.example;

import org.example.domain.Result;
import org.example.models.Location;
import org.example.models.Reservation;
import org.example.models.User;

import java.math.BigDecimal;
import java.time.LocalDate;


public class TestHelper {

    public static Reservation makeReservation(){
        User mockGuest = new User(1, "Llywellyn", "Vondra", "lvondra0@vkontakte.ru", "(940) 3612277");
        User mockHost = new User(2, "Roanna", "Klimpt", "rklimpt1@paginegialle.it", "(277) 2824355");
        Location mockLocation = new Location(1, mockHost, "6497 Manitowish Circle", "Melbourne", "FL", "32919", BigDecimal.valueOf(110.00), BigDecimal.valueOf(110.00));
        return new Reservation(1, mockLocation, mockGuest, LocalDate.of(2030,12,24), LocalDate.of(2030,12,30), BigDecimal.valueOf(100.00));
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
