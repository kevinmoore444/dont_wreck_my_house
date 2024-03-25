package org.example;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class DataHelper {

    //Datasource object is called in getJdbcTemplate. Datasource is wired into the template.
    public static DataSource getDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        // These are environment variables.
        dataSource.setUrl(System.getenv("DB_URL"));
        dataSource.setUser(System.getenv("DB_USERNAME"));
        dataSource.setPassword(System.getenv("DB_PASSWORD"));
        return dataSource;
    }
    //How will we use this template?
    public static JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }
}
