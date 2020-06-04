package com.chargingsession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Spring boot starter application. The Application launches from here
 * @author Praveen
 * 
 */
@SpringBootApplication
@EnableSwagger2
public class ChargingSessionApiApplication extends SpringBootServletInitializer {

	/**
	 * The application starts from here.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(ChargingSessionApiApplication.class, args);
	}

}
