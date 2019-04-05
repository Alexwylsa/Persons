package com.alexwylsa.Persons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class PersonsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonsApplication.class, args);

	}

}
