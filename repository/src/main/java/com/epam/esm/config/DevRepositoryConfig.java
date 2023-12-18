package com.epam.esm.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Configuration class for the development environment repository setup.
 * Configures data source, transaction manager, and JDBC template for development profile.
 */
@Configuration
@Profile("dev")
@EnableTransactionManagement
@ComponentScan("com.epam.esm")
@PropertySource("classpath:application-dev.properties")
public class DevRepositoryConfig {

    @Value("${db.pool.size}")
    private int poolSize;

    @Value("${db.driver}")
    private String driver;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${db.url}")
    private String url;

    /**
     * Creates and configures the data source based on properties for the development environment.
     *
     * @return Configured DataSource object.
     */
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setInitialSize(poolSize);
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * Creates a DataSourceTransactionManager using the configured DataSource for the development environment.
     *
     * @param dataSource The DataSource to be used by the transaction manager.
     * @return DataSourceTransactionManager object.
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * Creates a JdbcTemplate using the configured DataSource for the development environment.
     *
     * @param dataSource The DataSource to be used by the JdbcTemplate.
     * @return JdbcTemplate object.
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
