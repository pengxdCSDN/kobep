package com.xd.kobepjpa.config;

import com.xd.kobepjpa.repository.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef="entityManagerFactoryKobepjpa",
        transactionManagerRef="transactionManagerKobepjpa",
        basePackages= { "com.xd.kobepjpa.repository" },
        //设置baseRepository
        repositoryBaseClass = BaseRepositoryImpl.class
        )

public class KobepJpaConfig {

    @Autowired
    @Qualifier("kobepjpaDataSource")
    private DataSource kobepjpaDataSource;

    @Autowired
    private JpaProperties jpaProperties;

    @Primary
    @Bean(name = "entityManager")
    public EntityManager entityManager() {
        return entityManagerFactoryKobepjpa().getObject().createEntityManager();
    }


    @Primary
    @Bean(name = "entityManagerFactoryKobepjpa")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryKobepjpa () {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPersistenceUnitName("kobepjpaPersistenceUnit");
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.xd.kobepjpa.entity");
        factory.setDataSource(kobepjpaDataSource);
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", true);
        factory.setJpaProperties(properties);
        factory.afterPropertiesSet();
        return factory;
    }

    @Primary
    @Bean(name = "transactionManagerKobepjpa")
    public PlatformTransactionManager transactionManagerKobepjpa() {
        return new JpaTransactionManager(entityManagerFactoryKobepjpa().getObject());
    }


}
