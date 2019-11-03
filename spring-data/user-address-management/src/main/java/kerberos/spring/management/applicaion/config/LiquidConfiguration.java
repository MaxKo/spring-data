package kerberos.spring.management.applicaion.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class LiquidConfiguration {

    @Autowired
    private Environment env;


    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        //liquibase.setChangeLog("classpath:liquibase-changeLog.xml");
        liquibase.setChangeLog("classpath:db/changelog/v1.0/db.changelog-master.yaml");
        //liquibase.setChangeLog(
          //      "D:/Development/Git/GitHub/spring-data/user-address-management/src/main/resources/db.changelog/v1.0/db.changelog-master.yaml");

        //liquibase.set
        liquibase.setDataSource(dataSource());

        return liquibase;
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));

        return dataSource;
    }

}
