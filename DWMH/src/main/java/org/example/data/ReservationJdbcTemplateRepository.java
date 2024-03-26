package org.example.data;

import org.example.models.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;

public class ReservationJdbcTemplateRepository implements ReservationRepository{

    private final JdbcTemplate jdbcTemplate;


    public ReservationJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Find By Email Address
    @Override
    public List<Reservation> findReservationsByEmailAddress (String emailAddress){
        final String sql = """
                SELECT
                    r.reservation_id, r.start_date, r.end_date, r.guest_user_id, r.total,
                    gu.user_id AS guest_id, gu.first_name AS guest_first_name, gu.last_name AS guest_last_name,
                    gu.email AS guest_email, gu.phone AS guest_phone,
                    l.location_id, l.user_id AS host_id,
                    ho.first_name AS host_first_name, ho.last_name AS host_last_name,
                    ho.email AS host_email, ho.phone AS host_phone,
                    l.address, l.city, s.usps_code, l.standard_rate, l.weekend_rate
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

        int reservation_id = insert.executeAndReturnKey(args).intValue();
        reservation.setReservationId(reservation_id);
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation){
        final String sql = """
                UPDATE reservation SET
                	start_date = ?,
                	end_date = ?,
                	total = ?
                	WHERE reservation_id = ?;
                """;
        return jdbcTemplate.update(sql, reservation.getStartDate(), reservation.getEndDate(), reservation.getTotal(), reservation.getReservationId()) > 0;
    }

    @Override
    public boolean deleteById(int reservationId) {
        return jdbcTemplate.update("DELETE from reservation WHERE reservation_id = ?;", reservationId) > 0;
    }


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
                    l.address, l.city, s.usps_code, l.standard_rate, l.weekend_rate
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
