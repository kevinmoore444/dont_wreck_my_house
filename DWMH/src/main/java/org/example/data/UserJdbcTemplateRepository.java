package org.example.data;

import org.example.models.User;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserJdbcTemplateRepository implements UserRepository {
    //Wire in a JDBC Template object. This contains a repo of methods for submitting sql queries
    private final JdbcTemplate jdbcTemplate;

    //Repository Constructor - There is no empty constructor, must contain a jdbc template to function.
    public UserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public User findUserByID(int userId){
        final String sql = """
                SELECT
                    user_id, first_name, last_name, email, phone
                    FROM user 
                    WHERE user_id = ?;
                """;
        return jdbcTemplate.query(sql, new UserMapper(), userId)
                .stream().findFirst().orElse(null);
    }

    @Override
    public User findUserByEmail(String email){
        final String sql = """
                SELECT
                    user_id, first_name, last_name, email, phone
                    FROM user 
                    WHERE email = ?;
                """;
        return jdbcTemplate.query(sql, new UserMapper(), email)
                .stream().findFirst().orElse(null);
    }



}
