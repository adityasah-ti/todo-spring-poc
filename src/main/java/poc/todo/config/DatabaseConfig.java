package poc.todo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

/**
 * Configuration class for setting up database-related beans in a Spring application.
 */
@Configuration
public class DatabaseConfig {

    @Autowired
    private Environment env;

    /**
     * Creates a DataSource bean configured with environment properties.
     *
     * @return a DataSource configured with driver class name, URL, username, and password from the environment.
     */
    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("driverClassName"));
        dataSource.setUrl(env.getProperty("url"));
        dataSource.setUsername(env.getProperty("user"));
        dataSource.setPassword(env.getProperty("password"));
        return dataSource;
    }

    /**
     * Creates a primary DataSource bean configured for SQLite.
     *
     * @return a SQLiteDataSource configured with the SQLite database file and other settings.
     */
    @Bean
    @Primary
    DataSource createDataSource() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:todoSqlite.db");
        SQLiteConfig config = new SQLiteConfig();
        config.setDateClass("TEXT");
        dataSource.setConfig(config);
        return dataSource;
    }

    /**
     * Creates a JdbcTemplate bean for interacting with the database.
     *
     * @param dataSource the DataSource to be used by JdbcTemplate.
     * @return a JdbcTemplate configured with the provided DataSource.
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * Configuration class for SQLite database setup, activated under "sqlite" profile.
     */
    @Configuration
    @Profile("sqlite")
    @PropertySource("classpath:persistence.properties")
    static class SqliteConfig {
        // This configuration is specific to the "sqlite" profile.
    }
}
