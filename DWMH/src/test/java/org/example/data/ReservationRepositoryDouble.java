package org.example.data;

import org.example.models.Reservation;

import java.util.List;

import static org.example.TestHelper.makeReservation;

public class ReservationRepositoryDouble implements ReservationRepository {


    @Override
    public List<Reservation> findReservationsByEmailAddress(String emailAddress) {
            return List.of(makeReservation());
    }

    @Override
    public Reservation add(Reservation reservation) {
        return makeReservation();
    }

    @Override
    public boolean update(Reservation reservation) {
        return false;
    }

    @Override
    public boolean deleteById(int reservationId) {
        return reservationId == 1;
    }

    @Override
    public Reservation findByID(int reservationID) {
        return null;
    }
}
