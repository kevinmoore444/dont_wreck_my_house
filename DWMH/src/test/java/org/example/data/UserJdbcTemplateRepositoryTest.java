package org.example.data;

import org.example.DataHelper;
import org.example.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserJdbcTemplateRepositoryTest {


    //JDBC template contains a datasource object, which contains the login info and database url for mysql.
    //The database url, stored in intelliJ edit Configurations -> J-Unit - is what tells the repo to query the
    //test database.
    JdbcTemplate jdbcTemplate = DataHelper.getJdbcTemplate();
    //Construct an instance of the actual repo, with which we'll use to conduct tests on the test database.
    UserJdbcTemplateRepository repository = new UserJdbcTemplateRepository(jdbcTemplate);

    //This is a query stored in mysql workbench DWMH_Test database.
    // It resets the database before each test to a known state with some generic data for me to
    //test and query against.

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("call set_known_good_state();");
    }

    //Find By ID Test
    @Test
    void shouldFindById(){
        User user = repository.findUserByID(1);
        assertEquals(user.getFirst_name(), "FirstName1");
        assertEquals(user.getEmail(), "user1@gmail.com");
    }


    @Test
    void shouldFindByEmail(){
        User user = repository.findUserByEmail("user1@gmail.com");
        assertEquals(user.getFirst_name(), "FirstName1");

    }

}
