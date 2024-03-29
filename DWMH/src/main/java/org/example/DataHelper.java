package org.example;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class DataHelper {

    //JdbcTemplate requires a datasource object in order to be instantiated.
    //The datasource object needs a URL, User, and Password.
    //These are stored in edit configurations -> Application / J-Unit
    public static DataSource getDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        // These are environment variables.
        dataSource.setUrl(System.getenv("DB_URL"));
        dataSource.setUser(System.getenv("DB_USERNAME"));
        dataSource.setPassword(System.getenv("DB_PASSWORD"));
        return dataSource;
    }

    // getJdbcTemplate() is wired into the JDBC template in the App class when instantiating the repository.
    public static JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }
}
