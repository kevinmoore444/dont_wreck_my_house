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
        view.displayHostReservations(reservations, service.findLocationByEmail(hostEmail));
    }

    //#3 - Make a Reservation
    public void createReservation(){
        //Header
        view.printHeader("Make a Reservation");
        //Collect Host/Guest Email
        String guestEmail = view.getEmail("Guest Email");
        String hostEmail = view.getEmail("Host Email");
        //Display Current Host Reservations
        List<Reservation> reservations = service.findReservationByEmail(hostEmail);
        view.displayHostReservations(reservations, service.findLocationByEmail(hostEmail));
        //Read Start-End Date
        LocalDate startDate = view.getDate("Start (MM/dd/yyyy)");
        LocalDate endDate = view.getDate("End (MM/dd/yyyy)");

        //Obtain Location and Guest From the service
        Location location = service.findLocationByEmail(hostEmail);
        User guest = service.findUserByEmail(guestEmail);

        //Create Reservation Object from the Fields Provided
        Reservation reservation = new Reservation();
        reservation.setLocation(location);
        reservation.setGuest(guest);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setTotal(view.calculateTotal(reservation));

        //Print Summary
        view.printSummary(reservation);

        //Is this okay?
        boolean approval = view.getOkay("Is this okay? [y/n]");
        //Add Reservation via Service
        if(approval){
            Result<Reservation> result = service.add(reservation);
            if (result.isSuccess()){
                view.printSuccess("Reservation " + result.getPayload().getReservationId() + " has been created");
            }
            else {
                view.printErrors(result);
            }
        }
    }

    //#4 - Update a Reservation
    public void updateReservation() {
        //Header
        view.printHeader("Edit a Reservation");
        //Collect Host Email
        String hostEmail = view.getEmail("Host Email");
        //Display Current Host Reservations for potential update
        List<Reservation> reservations = service.findReservationByEmail(hostEmail);
        view.displayHostReservations(reservations, service.findLocationByEmail(hostEmail));
        //Read Reservation ID from User
        int reservationId = view.getID("Reservation ID");
        //Use service to find Reservation by ID
        Reservation reservation = service.findReservationById(reservationId);
        //Read Start-End Date from User
        LocalDate startDate = view.getDate("Start (MM/dd/yyyy)");
        LocalDate endDate = view.getDate("End (MM/dd/yyyy)");
        //Update Reservation Object w/Start,End Dates and Total Cost
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setTotal(view.calculateTotal(reservation));
        //Print Summary
        view.printSummary(reservation);
        //Is this okay?
        boolean approval = view.getOkay("Is this okay? [y/n]");
        if (approval) {
            //Update Reservation via Service
            Result<?> result = service.update(reservation);
            if (result.isSuccess()){
                view.printSuccess("Reservation " + reservation.getReservationId() + " has been updated");
            }
            else {
                view.printErrors(result);
            }
        }
    }
        //#5 - Delete a Reservation
        public void deleteReservation() {
            //Header
            view.printHeader("Cancel a Reservation");
            //Collect Host Email
            String hostEmail = view.getEmail("Host Email");
            //Display Current Host Reservations for potential update
            List<Reservation> reservations = service.findReservationByEmail(hostEmail);
            view.displayHostReservations(reservations, service.findLocationByEmail(hostEmail));
            //Read Reservation ID from User
            int reservationId = view.getID("Reservation ID");
            boolean approval = view.getOkay("Are you sure you want to delete? ID " + reservationId + " [y/n]");
            if (approval) {
                //Delete Reservation via Service
                Result<?> result = service.deleteById(reservationId);
                if (result.isSuccess()){
                    view.printSuccess("Reservation " + reservationId + " has been deleted");
                }
                else {
                    view.printErrors(result);
                }
            }
        }

}
