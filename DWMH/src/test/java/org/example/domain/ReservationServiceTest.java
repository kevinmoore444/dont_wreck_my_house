package org.example.domain;

import org.example.data.LocationRepositoryDouble;
import org.example.data.ReservationRepositoryDouble;
import org.example.data.UserRepositoryDouble;
import org.example.models.Reservation;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.example.TestHelper.makeReservation;
import static org.example.TestHelper.makeResult;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationServiceTest {
    ReservationService reservationService = new ReservationService(new ReservationRepositoryDouble(),
            new LocationRepositoryDouble(), new UserRepositoryDouble());


    //Find all Reservations associate w/ Host Email Address
    //The if it passes validation and service successfully calls the method,
    //The repository double will return a list of 1 reservation
    @Test
    void shouldFindWithValidEmail(){
        List<Reservation> reservationList = reservationService.findReservationByEmail("kevinmoore444@gmail.com");
        System.out.println(reservationList.size());
        assertEquals(reservationList.size(), 1);
    };

    //If Regex determines that the email is invalid, validation logic will return null.
    @Test
    void shouldNotFindWithInvalidEmail(){
        List<Reservation> nullResponse = reservationService.findReservationByEmail("kevinmoore444");
        assertEquals(nullResponse, null);
    }

    @Test
    void shouldAddValidReservation(){
        Reservation reservation = makeReservation(1);
        Result<Reservation> result = reservationService.add(reservation);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldUpdateValidReservation(){
        Reservation reservation = makeReservation(1);
        Result<?> result = reservationService.update(reservation);
        assertTrue(result.isSuccess());
    }



    @Test
    void shouldNotAddInvalidLocation(){
        //If the location id is 5 the repository double will return null to the service
        //and the service will register the error.
        Reservation reservation = makeReservation(1);
        reservation.getLocation().setLocationId(5);
        Result<Reservation> result = reservationService.add(reservation);
        assertEquals(result.getErrorMessages().get(0), "Location does not exist in our database");
    }

    @Test
    void shouldNotAddInvalidGuest(){
        Reservation reservation = makeReservation(1);
        reservation.getGuest().setUserId(5);
        Result<Reservation> result = reservationService.add(reservation);
        assertEquals(result.getErrorMessages().get(0), "Guest does not exist in our database");
    }



    //If null the service will return message "Reservation may not be null"
    //as it's result message
    @Test
    void shouldNotAddNull(){
        Reservation reservation = null;
        Result<Reservation> result = reservationService.add(reservation);
        assertEquals(result.getErrorMessages().get(0), "Reservation may not be null");
    }


    @Test
    void shouldNotAddMissingEndDate(){
        Reservation reservation = makeReservation(1);
        reservation.setEndDate(null);
        Result<Reservation> result = reservationService.add(reservation);
        assertEquals(result.getErrorMessages().get(0), "End Date Required");
    }

    @Test
    void shouldNotAddStartDateBeforeEndDate(){
        Reservation reservation = makeReservation(1);
        reservation.setEndDate(reservation.getStartDate().minusWeeks(3));
        Result<Reservation> result = reservationService.add(reservation);
        assertEquals(result.getErrorMessages().get(0), "Start date must come before end date");
    }

    @Test
    void shouldNotAddReservationDateOverlap(){
        //Creating reservation 2. When checking for overlap, the
        //repository-double  returns a list.of(reservation 2).
        //So this will create an overlap.
        Reservation reservation = makeReservation(2);
        Result<Reservation> result = reservationService.add(reservation);
        assertEquals(result.getErrorMessages().get(0), "Reservation overlaps with an existing reservation");
    }

    @Test
    void shouldNotAddStartDateInPast(){
        Reservation reservation = makeReservation(1);
        reservation.setStartDate(LocalDate.of(2020, 12,12));
        reservation.setEndDate(LocalDate.of(2020, 12,18));
        Result<Reservation> result = reservationService.add(reservation);
        assertEquals(result.getErrorMessages().get(0), "Start date cannot be in the past");
    }

    //Delete Reservation
    //If you successfully call deleteById via the service, the repository double will return true
    //And the service method will therefore return an empty result object.
    @Test
    void shouldDeleteValidReservationId(){
        Result<?> expected = makeResult(null, null);
        Result<?> actual = reservationService.deleteById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotDelete(){
        Result<?> expected = makeResult("Could not delete reservation 2", null);
        Result<?> actual = reservationService.deleteById(2);
        assertEquals(expected, actual);
    }



}
