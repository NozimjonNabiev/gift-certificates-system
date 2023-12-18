package com.epam.esm.config;

import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.repository.repository.GiftCertificateRepository;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.util.mapper.GiftCertificateRowMapper;
import com.epam.esm.util.mapper.TagRowMapper;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Configuration for repository test classes
 */
@Configuration
public class RepositoryTestConfig {

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(System.getProperty("db.url"));
        dataSource.setUsername(System.getProperty("db.username"));
        dataSource.setPassword(System.getProperty("db.password"));
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public TagRowMapper tagRowMapper() {
        return new TagRowMapper();
    }

    @Bean
    public GiftCertificateRowMapper giftCertificateRowMapper() {
        return new GiftCertificateRowMapper();
    }

    @Bean
    public TagRepository tagRepository(JdbcTemplate jdbcTemplate, TagRowMapper tagRowMapper) {
        return new TagRepositoryImpl(jdbcTemplate, tagRowMapper);
    }

    @Bean
    public GiftCertificateRepository giftCertificateRepository(
            JdbcTemplate jdbcTemplate, TagRowMapper tagRowMapper, GiftCertificateRowMapper certificateRowMapper) {
        return new GiftCertificateRepositoryImpl(jdbcTemplate, tagRowMapper, certificateRowMapper);
    }
}
