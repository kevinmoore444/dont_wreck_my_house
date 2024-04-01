package org.example.ui;

import org.example.domain.ReservationService;
import org.example.domain.Result;
import org.example.models.Location;
import org.example.models.Reservation;
import org.example.models.User;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;
import java.util.List;

public class Controller {
    //Dependencies
    private final View view;
    private final ReservationService service;

    //Controller
    public Controller(View view, ReservationService service) {
        this.view = view;
        this.service = service;
    }

    //Main Application Method
    public void run(){
        view.displayGreeting();
        try {
            runMenu();
        } catch (DataAccessException ex) {
            view.displayException(ex);
        }

    }

    //Run Menu
    private void runMenu() throws DataAccessException {
        MainMenuOption choice;
        do {
            choice = view.getMenuOption();
            switch (choice) {
                case View_Reservations -> viewReservationsByHost();
                case ADD -> createReservation();
                case UPDATE -> updateReservation();
                case DELETE -> deleteReservation();
            }
        } while (choice != MainMenuOption.EXIT);

        view.displayGoodbye();
    }

    //Run Menu Options

    //#2 - View Reservations for Host Location
    public void viewReservationsByHost(){
        view.printHeader("View Reservations For Host Location");
        String hostEmail = view.getEmail("Host Email");
        List<Reservation> reservations = service.findReservationByEmail(hostEmail);
        Result<Location> result = service.findLocationByEmail(hostEmail);
        view.displayHostReservations(reservations, result.getPayload());
    }

    //#3 - Make a Reservation
    public void createReservation(){
        //Header
        view.printHeader("Make a Reservation");

        //Collect Validate Guest Email,
        String guestEmail = view.getEmail("Guest Email");
        Result<User> guestResult = service.findUserByEmail(guestEmail);
        if(!guestResult.isSuccess()){
            view.printErrors(guestResult);
            return;
        }
        User guest = guestResult.getPayload();

        //Collect Validate Host/Location
        String hostEmail = view.getEmail("Host Email");
        Result<Location> locationResult = service.findLocationByEmail(hostEmail);
        if(!locationResult.isSuccess()){
            view.printErrors(locationResult);
            return;
        }
        Location hostLocation = locationResult.getPayload();

        //Display Current Host Reservations.
        List<Reservation> reservations = service.findReservationByEmail(hostEmail);
        view.displayHostReservations(reservations, hostLocation);

        //Read Start Date
        LocalDate startDate = view.readStartDate();

        //Read End Date
        LocalDate endDate = view.readEndDate(startDate);

        //Make Reservation Object w/ the Fields Provided
        Reservation reservation = view.makeReservation(hostLocation, guest, startDate, endDate);

        //Print Summary
        view.printSummary(reservation);

        //If okay, add Reservation via Service
        boolean approval = view.getOkay("Is this okay? [y/n]");
        if(approval){
            Result<Reservation> serviceResult = service.add(reservation);
            if (!serviceResult.isSuccess()){
                view.printErrors(serviceResult);
            }
            else {
                view.printSuccess("Reservation " + serviceResult.getPayload().getReservationId() + " has been created");
            }
        }
    }

    //#4 - Update a Reservation
    public void updateReservation() {
        //Header
        view.printHeader("Edit a Reservation");

        //Collect Host Email and display host reservations available for update
        String hostEmail = view.getEmail("Host Email");
        List<Reservation> reservations = service.findReservationByEmail(hostEmail);
        Result<Location> locationResult = service.findLocationByEmail(hostEmail);
        if(!locationResult.isSuccess()){
            view.printErrors(locationResult);
            return;
        }
        view.displayHostReservations(reservations, locationResult.getPayload());

        //Read Reservation ID from User - user must select ID from list of found reservations.
        int reservationId = view.getIdForDeletion(reservations);
        if (reservationId == 0){
            return;
        }
        //Use service to find Reservation by ID
        Reservation reservation = service.findReservationById(reservationId);


        //Read Start-End Date from User
        LocalDate startDate = view.readStartDate();
        LocalDate endDate = view.readEndDate(startDate);

        //Update Reservation Object w/Start,End Dates and Total Cost
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setTotal(view.calculateTotal(reservation));
        //Print Summary
        view.printSummary(reservation);

        //If okay, update reservation via service
        boolean approval = view.getOkay("Is this okay? [y/n]");
        if (approval) {
            Result<?> updateResult = service.update(reservation);
            if (!updateResult.isSuccess()){
                view.printErrors(updateResult);
            }
            else {
                view.printSuccess("Reservation " + reservation.getReservationId() + " has been updated");
            }
        }
    }
        //#5 - Delete a Reservation
        public void deleteReservation() {
            //Header
            view.printHeader("Cancel a Reservation");

            //Collect Host Email
            String hostEmail = view.getEmail("Host Email");

            //Use Host Email to find reservations and location details to display
            List<Reservation> reservations = service.findReservationByEmail(hostEmail);
            Result<Location> result = service.findLocationByEmail(hostEmail);
            view.displayHostReservations(reservations, result.getPayload());

            //Read Reservation ID from User - user must select ID from list of found reservations.
            int reservationId = view.getIdForDeletion(reservations);
            if (reservationId == 0){
                return;
            }

            //Get approval, delete Reservation via Service
            boolean approval = view.getOkay("Are you sure you want to delete ID " + reservationId + "? [y/n]");
            if (approval) {
                Result<?> deleteResult = service.deleteById(reservationId);
                if (!deleteResult.isSuccess()){
                    view.printErrors(deleteResult);
                }
                else {
                    view.printSuccess("Reservation " + reservationId + " has been deleted");
                }
            }
        }
}
