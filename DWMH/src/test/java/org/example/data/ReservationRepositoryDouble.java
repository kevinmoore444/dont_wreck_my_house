package org.example.data;

import org.example.models.Reservation;

import java.util.List;

import static org.example.TestHelper.makeReservation;

public class ReservationRepositoryDouble implements ReservationRepository {


    @Override
    public List<Reservation> findReservationsByEmailAddress(String emailAddress) {
        //Returns reservation 2. If adding reservation one, this will pass the overlapping dates validation.
        return List.of(makeReservation(2));
    }

    @Override
    public Reservation add(Reservation reservation) {
        //This is a valid reservation unless otherwise updated.
        return makeReservation(1);
    }

    @Override
    public boolean update(Reservation reservation) {
        return reservation.getReservationId() == 1;
    }

    @Override
    public boolean deleteById(int reservationId) {
        //If you delete by id 1, and it passes the validation, you will return true.
        return reservationId == 1;
    }

    @Override
    public Reservation findByID(int reservationID) {
        //There is no service level validation of this method, so no need to test it.
        return null;
    }
}
