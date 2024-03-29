package org.example.data;

import org.example.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        User user = new User();
        //Compose a user based upon the row returned in the result set. Column labels defined in sql.
        user.setUserId(rs.getInt("user_id"));
        user.setFirst_name(rs.getString("first_name"));
        user.setLast_name(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));

        return user;
    }
}
