package org.example.data;

import org.example.models.Location;
import org.example.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationMapper implements RowMapper<Location> {

    @Override
    public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
        Location location = new Location();
        //Compose a location based upon the row returned in the result set. Column labels defined in sql.
        location.setLocationId(rs.getInt("location_id"));
        location.setAddress(rs.getString("address"));
        location.setCity(rs.getString("city"));
        location.setPostalCode(rs.getString("postal_code"));
        location.setStandardRate(rs.getBigDecimal("standard_rate"));
        location.setWeekendRate(rs.getBigDecimal("weekend_rate"));

        //Will need to join state table in order to fetch usps code.
        location.setState(rs.getString("usps_code"));

        //Compose a User object as a host. This object will be set to the location object.
        User host = new User();
        host.setUserId(rs.getInt("host_id"));
        host.setFirst_name(rs.getString("host_first_name"));
        host.setLast_name(rs.getString("host_last_name"));
        host.setEmail(rs.getString("host_email"));
        host.setPhone(rs.getString("host_phone"));

        location.setHost(host);





        return location;
    }
}
