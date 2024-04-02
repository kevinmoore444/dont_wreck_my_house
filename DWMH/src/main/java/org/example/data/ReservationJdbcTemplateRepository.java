package org.example.data;

import org.example.models.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;

public class ReservationJdbcTemplateRepository implements ReservationRepository{
    //Wire in a JDBC Template object. This contains a repo of methods for submitting sql queries
    private final JdbcTemplate jdbcTemplate;

    //Repository Constructor - There is no empty constructor, must contain a jdbc template to function.
    public ReservationJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Find By Email Address
    @Override
    public List<Reservation> findReservationsByEmailAddress (String emailAddress){
        //Must select all data fields which will be used in the Reservation RowMapper
        // in order to map out a Reservation object
        final String sql = """
                SELECT
                    r.reservation_id, r.start_date, r.end_date, r.guest_user_id, r.total,
                    gu.user_id AS guest_id, gu.first_name AS guest_first_name, gu.last_name AS guest_last_name,
                    gu.email AS guest_email, gu.phone AS guest_phone,
                    l.location_id, l.user_id AS host_id,
                    ho.first_name AS host_first_name, ho.last_name AS host_last_name,
                    ho.email AS host_email, ho.phone AS host_phone,
                    l.address, l.city, l.postal_code, s.usps_code, l.standard_rate, l.weekend_rate
                    FROM
                    reservation r
                    INNER JOIN user gu ON r.guest_user_id = gu.user_id
                    INNER JOIN location l ON r.location_id = l.location_id
                    INNER JOIN state s ON s.state_id = l.state_id
                    INNER JOIN user ho ON l.user_id = ho.user_id
                    WHERE ho.email = ?;
                """;

        return jdbcTemplate.query(sql, new ReservationMapper(), emailAddress);
    }
    //Add Reservation
    @Override
    public Reservation add(Reservation reservation){
        //Create an insert object. This object has executeAndReturnKey method built into it.
        // ExecuteAndReturnKey takes in a hashmap of keys (sql column names) and the corresponding value for that column,
        // and returns the generated key which is specified (reservation_id).
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingColumns("location_id", "guest_user_id", "start_date", "end_date", "total")
                .usingGeneratedKeyColumns("reservation_id")  ;


        HashMap<String, Object> args = new HashMap<>();
        args.put("location_id", reservation.getLocation().getLocationId());
        args.put("guest_user_id", reservation.getGuest().getUserId());
        args.put("start_date", reservation.getStartDate());
        args.put("end_date", reservation.getEndDate());
        args.put("total", reservation.getTotal());
        //The object which is passed into the method is complete except that it's lacking an id.
        //After sql creates this objects id, we set it, and then return the completed object to the user.
        int reservation_id = insert.executeAndReturnKey(args).intValue();
        reservation.setReservationId(reservation_id);
        return reservation;
    }


    @Override
    public boolean update(Reservation reservation){
        //User only has option to update start and end date. These will update the total by definition.
        final String sql = """
                UPDATE reservation SET
                	start_date = ?,
                	end_date = ?,
                	total = ?
                	WHERE reservation_id = ?;
                """;
        //The return is 1 if successful, 0 if unsuccessful. So this syntax returns a true or false to the service
        return jdbcTemplate.update(sql, reservation.getStartDate(), reservation.getEndDate(), reservation.getTotal(), reservation.getReservationId()) > 0;
    }

    //Delete Reservation By ID.
    //Reservation does not exist as a dependency to other tables, so there is no cascading, simple delete.
    @Override
    public boolean deleteById(int reservationId) {
        return jdbcTemplate.update("DELETE from reservation WHERE reservation_id = ?;", reservationId) > 0;
    }

    //Find by ID
    //Same as find by Email address, but we change the WHERE clause
    //And return only one reservation instead of a list - hence the use of stream().findfirst()
    @Override
    public Reservation findByID(int reservationID){
        final String sql = """
                SELECT
                    r.reservation_id, r.start_date, r.end_date, r.guest_user_id, r.total,
                    gu.user_id AS guest_id, gu.first_name AS guest_first_name, gu.last_name AS guest_last_name,
                    gu.email AS guest_email, gu.phone AS guest_phone,
                    l.location_id, l.user_id AS host_id,
                    ho.first_name AS host_first_name, ho.last_name AS host_last_name,
                    ho.email AS host_email, ho.phone AS host_phone,
                    l.address, l.city, l.postal_code, s.usps_code, l.standard_rate, l.weekend_rate
                    FROM
                    reservation r
                    INNER JOIN user gu ON r.guest_user_id = gu.user_id
                    INNER JOIN location l ON r.location_id = l.location_id
                    INNER JOIN state s ON s.state_id = l.state_id
                    INNER JOIN user ho ON l.user_id = ho.user_id
                    WHERE reservation_id = ?;
                """;
    return jdbcTemplate.query(sql, new ReservationMapper(), reservationID)
            .stream().findFirst().orElse(null);
    }





}
