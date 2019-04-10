package com.alexwylsa.Persons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
//connection to test database
@Configuration
@ComponentScan(basePackages = "com.alexwylsa.Persons")
@EnableJpaRepositories(basePackages = "com.alexwylsa.Persons.repo")
@PropertySource("classpath:testApplication.properties")
@AutoConfigureTestEntityManager
@EnableTransactionManagement
@EnableAutoConfiguration
public class ApplicationTest {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource(){

        InputStream is = ApplicationTest.class.getResourceAsStream("/testApplication.properties");
        Properties props = new Properties();
        try {
            props.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("!!!" + props);
        return DataSourceBuilder.create()
                .username(props.getProperty("spring.datasource.username"))
                .password(props.getProperty("spring.datasource.password"))
                .url(props.getProperty("spring.datasource.url"))
                .build();
    }
}
