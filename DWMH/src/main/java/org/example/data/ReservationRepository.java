package org.example.data;

import org.example.models.Reservation;

import java.util.List;

//All the methods employed by the ReservationJdbcTemplateRepository and the RepositoryDouble used to test the service.
public interface ReservationRepository {

    List<Reservation> findReservationsByEmailAddress (String emailAddress);

    Reservation add(Reservation reservation);

    boolean update(Reservation reservation);

    boolean deleteById(int reservationId);

    Reservation findByID(int reservationID);

}

