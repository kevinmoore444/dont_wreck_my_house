package org.example.data;

import org.example.models.Location;
import org.example.models.Reservation;
import org.example.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationMapper implements RowMapper<Reservation>  {


    @Override
    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
        //Start building a reservation
        Reservation reservation = new Reservation();
        reservation.setReservationId(rs.getInt("reservation_id"));
        reservation.setStartDate(rs.getDate("start_date").toLocalDate());
        reservation.setEndDate(rs.getDate("end_date").toLocalDate());
        reservation.setTotal(rs.getBigDecimal("total"));

        //Compose a Guest to be added to reservation
        User guest = new User();
        guest.setUserId(rs.getInt("guest_id"));
        guest.setFirst_name(rs.getString("guest_first_name"));
        guest.setLast_name(rs.getString("guest_last_name"));
        guest.setEmail(rs.getString("guest_email"));
        guest.setPhone(rs.getString("guest_phone"));
        reservation.setGuest(guest);

        //Compose a Host to be added to location
        User host = new User();
        host.setUserId(rs.getInt("host_id"));
        host.setFirst_name(rs.getString("host_first_name"));
        host.setLast_name(rs.getString("host_last_name"));
        host.setEmail(rs.getString("host_email"));
        host.setPhone(rs.getString("host_phone"));


        //Compose a Location to be added to reservation
        Location location = new Location();
        location.setLocationId(rs.getInt("location_id"));
        location.setHost(host);
        location.setAddress(rs.getString("address"));
        location.setCity(rs.getString("city"));
        location.setState(rs.getString("usps_code"));
        location.setStandardRate(rs.getBigDecimal("standard_rate"));
        location.setWeekendRate(rs.getBigDecimal("weekend_rate"));
        reservation.setLocation(location);

        return reservation;
    }
}
