package org.example.ui;

import org.example.domain.Result;
import org.example.models.Location;
import org.example.models.Reservation;
import org.example.models.User;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class View {

    //Dependencies
    private final TextIO io;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    //Constructor
    public View(TextIO io) {
        this.io = io;
    }


    //Generic Methods

    //Get Menu Option
    public MainMenuOption getMenuOption() {
        return io.readEnum("Main Menu", MainMenuOption.class);
    }

    //Get Host Email
    public String getEmail(String prompt){
        String email = io.readRequiredString(prompt);
        return email;
    }

    //Get Date
    public LocalDate getDate(String prompt){
        LocalDate date = io.readLocalDate(prompt);
        return date;
    }

    //Get int-ID
    public int getID(String prompt){
        int id = io.readInt(prompt);
        return id;
    }


    //Get Okay
    public boolean getOkay(String prompt){
        boolean response = io.readBoolean(prompt);
        return response;
    }

    //Print Summary
    public void printSummary(Reservation reservation){
        printHeader("Summary");
        System.out.printf("Start: %s\n", reservation.getStartDate());
        System.out.printf("End: %s\n", reservation.getEndDate());
        System.out.printf("Total: $%.2f\n\n", reservation.getTotal());

    }

    //Calculate Total
    public BigDecimal calculateTotal(Reservation reservation){
        //Instantiate Total
        BigDecimal totalDue = BigDecimal.ZERO;
        //Obtain parameters
        LocalDate startDate = reservation.getStartDate();
        LocalDate endDate = reservation.getEndDate();
        BigDecimal standardRate = reservation.getLocation().getStandardRate();
        BigDecimal weekendRate = reservation.getLocation().getWeekendRate();

        LocalDate currentDate = startDate;
        //End Date is not charged
        while (!currentDate.isEqual(endDate)){
            //Charge weekend rate on Friday and Saturday
            if (currentDate.getDayOfWeek() == DayOfWeek.FRIDAY ||
                    currentDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
                totalDue = totalDue.add(weekendRate);
            } else {
                totalDue = totalDue.add(standardRate);
            }
            currentDate = currentDate.plusDays(1);
        }
        return totalDue;
    }

    //Print Success
    public void printSuccess(String message){
        printHeader("Success");
        System.out.println(message);
    }

    //Print Errors
    public void printErrors(Result<?> result){
        System.out.println("");
        for (String message : result.getErrorMessages()){
            System.out.println(message);
        }
    }







    //Display Header
    public void printHeader(String prompt){
        io.printHeader(prompt);
    }

    //Welcome Methods
    public void displayGreeting() {
        io.println("Welcome to Reservation Manager - Don't Wreck My House");
    }
    public void displayException(Exception ex) {
        io.printHeader("Critical Error");
        io.println(ex.getMessage());
    }

    public void displayGoodbye() {
        io.println("Goodbye.");
    }


    //View All Host Reservations
    public void displayHostReservations(List<Reservation> all, Location hostLocation) {
        if(hostLocation == null){
            io.println("\nHost Not Found\n");
            return;
        }
        String hostName = hostLocation.getHost().getFirst_name() + " " + hostLocation.getHost().getLast_name();
        io.printf("\n%s\n%s\n%s, %s %s\n",
                hostName, hostLocation.getAddress(), hostLocation.getCity(), hostLocation.getState(), hostLocation.getPostalCode());
        io.printf("=================\n");
        //If Host Has No Reservations
        if (all == null || all.isEmpty()) {
            io.println("\nNo reservations found.\n");
            return;
        }
        //Else - Print Out All Reservations
        for (Reservation reservation : all){
            io.printf("ID: %d, %s - %s, Guest: %s, %s Email: %s\n",
                    reservation.getReservationId(), reservation.getStartDate().format(formatter), reservation.getEndDate().format(formatter),
                    reservation.getGuest().getLast_name(), reservation.getGuest().getLast_name(),
                    reservation.getGuest().getEmail());
        }
    }


    //Create Reservation Methods

    //Make Reservation
    public Reservation makeReservation(Location hostLocation, User guest, LocalDate startDate, LocalDate endDate){
        //Create new Reservation Object
        Reservation reservation = new Reservation();
        //Set Reservation Object w/ Fields Provided
        reservation.setLocation(hostLocation);
        reservation.setGuest(guest);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setTotal(calculateTotal(reservation));

        return reservation;
    }

    //Read Start Date
    public LocalDate readStartDate(){
        while(true) {
            LocalDate startDate = getDate("Start (MM/dd/yyyy)");
            if (startDate.isBefore(LocalDate.now())){
                System.out.println("\nError: Please input a start date in the future\n");
            }
            else{
                return startDate;
            }
        }
    }

    public LocalDate readEndDate(LocalDate startDate){
        while(true) {
            LocalDate endDate = getDate("End (MM/dd/yyyy)");
            if (endDate.isBefore(startDate)){
                System.out.println("\nError: End Date is Before the Start Date\n");
            }
            else{
                return endDate;
            }
        }
    }


    public int getIdForDeletion(List<Reservation> reservations){
        while(true) {
            int idForDeletion = getID("Reservation ID");

            for(Reservation reservation : reservations){
                if(idForDeletion == reservation.getReservationId()){
                    return idForDeletion;
                }
                if(idForDeletion == 0){
                    return 0;
                }
            }
            System.out.println("Please enter an ID from the list or enter 0 to exit");
        }
    }

}
