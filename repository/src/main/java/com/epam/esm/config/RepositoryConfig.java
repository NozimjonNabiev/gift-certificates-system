package com.epam.esm.config;

import lombok.RequiredArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Configuration class for the production environment repository setup.
 * Configures data source, transaction manager, and JDBC template for production profile.
 */
@Configuration
@Profile("prod")
@RequiredArgsConstructor
@EnableTransactionManagement
@ComponentScan("com.epam.esm")
@PropertySource("classpath:application.properties")
public class RepositoryConfig {
    @Value("${db.pool.size}")
    public String POOL_SIZE;
    @Value("${db.driver}")
    public String DRIVER;
    @Value("${db.username}")
    public String USERNAME;
    @Value("${db.password}")
    public String PASSWORD;
    @Value("${db.url}")
    public String URL;

    /**
     * Creates and configures the data source based on properties for the production environment.
     *
     * @return Configured DataSource object.
     */
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setInitialSize(Integer.parseInt(POOL_SIZE));
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        return dataSource;
    }

    /**
     * Creates a DataSourceTransactionManager using the configured DataSource for the production environment.
     *
     * @param dataSource The DataSource to be used by the transaction manager.
     * @return DataSourceTransactionManager object.
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * Creates a JdbcTemplate using the configured DataSource for the production environment.
     *
     * @param dataSource The DataSource to be used by the JdbcTemplate.
     * @return JdbcTemplate object.
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
