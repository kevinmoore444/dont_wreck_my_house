package org.example.domain;

import org.example.data.LocationRepositoryDouble;
import org.example.data.ReservationRepositoryDouble;
import org.example.data.UserRepositoryDouble;
import org.example.models.Reservation;
import org.junit.jupiter.api.Test;

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



    //Add /Update / Validate Reservation

    //Make a valid reservation via makeReservation
    //Set that same object as the payload of a result object.
    //If the reservation passes validation, the service will call
    // the repository double, which will return that same object.
    @Test
    void shouldAddValidReservation(){
        Reservation reservation = makeReservation();
        Result<Reservation> mockResult = makeResult(null, makeReservation());
        Result<Reservation> actualResult = reservationService.add(reservation);
        assertTrue(mockResult.equals(actualResult) );
    }



    @Test
    void shouldUpdateValidReservation(){}


    //If null the service will return "Reservation may not be null"
    //as it's result message
    @Test
    void shouldNotAddNull(){
        Reservation reservation = null;
        Result<Reservation> result = reservationService.add(reservation);
        System.out.println(result.getErrorMessages());
        assertEquals(result.getErrorMessages().get(0), "Reservation may not be null");
    }


    @Test
    void shouldNotAddMissingEndDate(){

    }


    @Test
    void shouldNotAddInvalidGuest(){

    }


    @Test
    void shouldNotAddInvalidLocation(){

    }



    @Test
    void shouldNotAddStartDateBeforeEndDate(){

    }

    @Test
    void shouldNotAddReservationDateOverlap(){

    }



    @Test
    void shouldNotAddStartDateInPast(){

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
