package com.flight.flight_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestFlightSpringApplication {

	public static void main(String[] args) {
		SpringApplication.from(FlightSpringApplication::main).with(TestFlightSpringApplication.class).run(args);
	}

}
