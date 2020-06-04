package com.chargingsession.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import com.chargingsession.model.ChargingSession;
import com.chargingsession.service.IChargingSessionService;
import com.chargingsession.service.impl.ChargingSessionService;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Common Bean configuration
 * @author Praveen
 * 
 */
@Configuration
@EnableSwagger2
public class BeanConfiguration {
	
	/**
	 * Bean for ChargingSessionService instance
	 *
	 * @return the charging session service instance
	 */
	@Bean
	public IChargingSessionService<ChargingSession> chargingService(){
		return new ChargingSessionService();
	}
	
	/**
	 * API bean for Docket object for swagger.
	 *
	 * @return the docket
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).paths(PathSelectors.any())
				.build().apiInfo(apiInfo());
	}

	/**
	 * ApiInfo object which contains REST API basic info.
	 *
	 * @return the api info
	 */
	private ApiInfo apiInfo() {
		return new ApiInfo("Charging Session API","API for Charging Session functionality", "v1",
				"", new springfox.documentation.service.Contact("Praveen", "none","praveen.itian@gmail.com"),
				"None", "None", Collections.emptyList());
		}
}
