package com.b2i.activitiesorganisation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableFeignClients
@EnableScheduling
//@EnableDiscoveryClient
public class ActivitiesOrganisationApplication extends SpringBootServletInitializer {

	// EXTENDS SPRING BOOT SERVLET INITIALIZER FOR CREATING A DEPLOYABLE WAR
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ActivitiesOrganisationApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ActivitiesOrganisationApplication.class, args);
	}
}
