package com.trodix.migration.core.configuration;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {

    /**
     * datasource h2 pour spring batch
     */

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.spring-batch")
    public DataSourceProperties springBatchDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.spring-batch")
    public DataSource springBatchDataSource() {
        return springBatchDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    /**
     * datasource postgresql pour la database metier
     */

    @Bean
    @ConfigurationProperties("app.datasource.todo-data")
    public DataSourceProperties todoDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource.todo-data")
    public DataSource todoDataSource() {
        return todoDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

}
