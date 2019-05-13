package com.quick.start.utils;

import javax.sql.DataSource;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;

import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration Class for changing the default schema for tests
 */
@Configuration
public class TestDatabaseConfig {

    @Value("${spring.jpa.hibernate.default_schema}")
    private String testSchema;

    @Bean
    public DatabaseConfigBean dbUnitDatabaseConfig() {
        DatabaseConfigBean databaseConfigBean = new DatabaseConfigBean();
        final PostgresqlDataTypeFactory dataTypeFactory = new PostgresqlDataTypeFactory();
        databaseConfigBean.setDatatypeFactory(dataTypeFactory);
        return databaseConfigBean;
    }

    @Bean
    public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection(DataSource dataSource) {
        final DatabaseDataSourceConnectionFactoryBean bean =
                new DatabaseDataSourceConnectionFactoryBean(dataSource);
        bean.setDatabaseConfig(dbUnitDatabaseConfig());
        bean.setSchema(this.testSchema);
        return bean;
    }
}