package com.api.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	Contact contact = new Contact("Jonathan Hirshowitz", "https://github.com/YoniWitz/", "jonhirsh39@gmail.com");

	List<VendorExtension> vendorExtension = new ArrayList<>();

	ApiInfo apiInfo = new ApiInfo(
		"User Api using Spring Boot", 
		"This page documents RESTful Web Service endpoints",
			"1.0", 
			"https://jonathan-hirshowitz-portfolio.firebaseapp.com/", 
			contact, 
			"Apache 2.0",
			 "http://www.apache.org/license/LICENSE-2.0", 
			 vendorExtension);

	@Bean
	public Docket apiDocket() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
		.protocols(new HashSet<>(Arrays.asList("HTTP", "HTTPS")))
				.apiInfo(apiInfo)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.appsdeveloperblog.app.ws"))
				.paths(PathSelectors.any()).build();

		return docket;
	}
}
