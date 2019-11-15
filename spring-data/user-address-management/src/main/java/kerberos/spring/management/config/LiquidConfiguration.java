package kerberos.spring.management.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class LiquidConfiguration {

    public static final String CLASSPATH_DB_CHANGELOG_V_1_0_DB_CHANGELOG_MASTER_YAML = "classpath:db/changelog/v1.0/db.changelog-master.yaml";
    public static final String SPRING_DATASOURCE_DRIVER_CLASS_NAME = "spring.datasource.driverClassName";
    public static final String SPRING_DATASOURCE_URL = "spring.datasource.url";
    public static final String SPRING_DATASOURCE_USERNAME = "spring.datasource.username";
    public static final String SPRING_DATASOURCE_PASSWORD = "spring.datasource.password";

    @Autowired
    private Environment environment;


    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(CLASSPATH_DB_CHANGELOG_V_1_0_DB_CHANGELOG_MASTER_YAML);
        liquibase.setDataSource(dataSource());

        return liquibase;
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(environment.getProperty(SPRING_DATASOURCE_DRIVER_CLASS_NAME));
        dataSource.setUrl(environment.getProperty(SPRING_DATASOURCE_URL));
        dataSource.setUsername(environment.getProperty(SPRING_DATASOURCE_USERNAME));
        dataSource.setPassword(environment.getProperty(SPRING_DATASOURCE_PASSWORD));

        return dataSource;
    }

}
