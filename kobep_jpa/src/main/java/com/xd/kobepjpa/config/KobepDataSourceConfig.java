package com.xd.kobepjpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class KobepDataSourceConfig {


    @Bean(name = "kobepjpaDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.kobepjpa")
    public DataSource kobepjpaDataSource() {

        return DataSourceBuilder.create().build();
    }


}
