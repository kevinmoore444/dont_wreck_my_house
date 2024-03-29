package org.example.data;

import org.example.models.Location;
import org.springframework.jdbc.core.JdbcTemplate;

public class LocationJdbcTemplateRepository implements LocationRepository{
    //Wire in a JDBC Template object. This contains a repo of methods for submitting sql queries
    private final JdbcTemplate jdbcTemplate;

    //Repository Constructor - There is no empty constructor, must contain a jdbc template to function.
    public LocationJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Location findLocationByID(int locationId){
        final String sql = """
                SELECT
                    l.location_id, l.address, l.city, l.postal_code, 
                    l.standard_rate, l.weekend_rate, s.usps_code, 
                    l.user_id as host_id, 
                    ho.first_name AS host_first_name, ho.last_name AS host_last_name,
                    ho.email AS host_email, ho.phone AS host_phone
                    FROM location l
                    INNER JOIN user ho ON l.user_id = ho.user_id
                    INNER JOIN state s ON s.state_id = l.state_id
                    WHERE location_id = ?;
                """;
        return jdbcTemplate.query(sql, new LocationMapper(), locationId)
                .stream().findFirst().orElse(null);
    }

}
