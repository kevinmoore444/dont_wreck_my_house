package org.example.domain;

import org.example.data.LocationRepository;
import org.example.data.ReservationRepository;
import org.example.data.UserRepository;
import org.example.models.Location;
import org.example.models.Reservation;
import org.example.models.User;

import java.time.LocalDate;
import java.util.List;

import static org.example.domain.Validation.isValidEmail;

public class ReservationService {
    //Attributes - need a reservation repo for the service to call.
    private final ReservationRepository reservationRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    //Overloaded Constructor - user must wire in a repo
    public ReservationService(ReservationRepository reservationRepository, LocationRepository locationRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    //Find Reservations By Email Address
    public List<Reservation> findReservationByEmail(String emailAddress){
        //Check that input is a valid email address.
        if(!isValidEmail(emailAddress)){
            return null;
        }
        return reservationRepository.findReservationsByEmailAddress(emailAddress);
    }

    //Add Reservation
    public Result<Reservation> add(Reservation reservation){
        //Validate Method is defined below. Ensure the reservation meets
        //structured requirements.
        Result<Reservation> result = validate(reservation);
        if(!result.isSuccess()){
            return result;
        }
        //Attempt to call repo to create reservation.
        Reservation created_reservation = reservationRepository.add(reservation);
        //Add the created reservation to result object
        result.setPayload(created_reservation);
        //Return the created reservation to the UI.
        return result;
    }

    //Update Reservation
    //The result type is a wildcard (or void) because the payload of the result will be null.
    //Unlike the add reservation method, which will contain a payload of that newly created reservation.
    public Result<?> update(Reservation reservation){
        //Validate Method is defined below. Ensure the reservation meets
        //structured requirements.
        Result<Reservation> result = validate(reservation);
        if(!result.isSuccess()){
            return result;
        }
        //Update method returns a boolean.
        boolean success = reservationRepository.update(reservation);
        //If failed, add message to the result object.
        if(!success){
            String msg = String.format("Could not update reservation %s", reservation.getReservationId());
            result.addErrorMessage(msg);
        }

        return result;
    }

    //Delete Reservation By ID
    //The result type is a wildcard (or void) because the payload of the result will be null.
    //Unlike the add reservation method, which will contain a payload of that newly created reservation.
    public Result<?> deleteById(int reservationId){
        Result<?> result = new Result<>();
        //Delete method returns a boolean
        boolean success = reservationRepository.deleteById(reservationId);
        //If failed, add message to the result object.
        if(!success){
            String msg = String.format("Could not delete reservation %s", reservationId);
            result.addErrorMessage(msg);
        }
        return result;
    }

    //Find Reservation By ID
    public Reservation findReservationById(int reservationId){
        return reservationRepository.findByID(reservationId);
    }

    //Validate
    private Result<Reservation> validate(Reservation reservation){
        Result<Reservation> result = new Result<>();
       //Ensure reservation isn't null
        if(reservation == null){
            result.addErrorMessage("Reservation may not be null");
            return result;
        }
        //Ensure the reservation fields are provided.
        if(reservation.getStartDate() == null){
            result.addErrorMessage("Start Date Required");
        }
        if(reservation.getEndDate() == null){
            result.addErrorMessage("End Date Required");
        }
        if(reservation.getLocation() == null){
             result.addErrorMessage("Location is required");
        }
        if (reservation.getGuest() == null){
             result.addErrorMessage("Guest was not found");
        }
        if(!result.isSuccess()){
            return result;
        }

        //Ensure the guest and host exist in the database.
        if(locationRepository.findLocationByID(reservation.getLocation().getLocationId()) == null){
            result.addErrorMessage("Location does not exist in our database");
        }
        if(userRepository.findUserByID(reservation.getGuest().getUserId()) == null){
            result.addErrorMessage("Guest does not exist in our database");
        }

        //Ensure the start date comes before the end date.
        if (reservation.getEndDate().isBefore(reservation.getStartDate())) {
            result.addErrorMessage("Start date must come before end date");
        }

        //Ensure the reservation doesn't overlap existing reservation dates
        if(doesOverlap(reservation)){
            result.addErrorMessage("Reservation overlaps with an existing reservation");
        }

        //Ensure start date is in the future.
        if(reservation.getStartDate().isBefore(LocalDate.now())){
            result.addErrorMessage("Start date cannot be in the past");
        }


        return result;
    }

    //Find Location By Host Email
    public Location findLocationByEmail(String email){
        Location location = locationRepository.findLocationByEmail(email);
        return location;
    }

    //Find  User By ID
    public User findUserByEmail(String email){
        User user = userRepository.findUserByEmail(email);
        return user;
    }


    //Is Overlapping
    public boolean doesOverlap(Reservation reservation) {
        //Find List of Reservations by Host:
        List<Reservation> hostReservations = reservationRepository.findReservationsByEmailAddress(reservation.getLocation().getHost().getEmail());
        LocalDate newStartDate = reservation.getStartDate();
        LocalDate newEndDate = reservation.getEndDate();
        //Iterate over the list of Reservations
        for (Reservation existingReservation : hostReservations) {
            LocalDate existingStartDate = existingReservation.getStartDate();
            LocalDate existingEndDate = existingReservation.getEndDate();

            // Check if the new reservation overlaps with any existing reservation
            if (newStartDate.isBefore(existingEndDate) && newEndDate.isAfter(existingStartDate)) {
                // Overlapping dates found
                return true;
            }
        }
        // No overlapping dates found
        return false;
    }




}
