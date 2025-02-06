package com.grupo1.ms_buscador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MsBuscadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsBuscadorApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new io.swagger.v3.oas.models.info.Info()
						.title("Buscador API")
						.termsOfService("http://swagger.io/terms/")
						.version("1.0")
						.license(new io.swagger.v3.oas.models.info.License().name("Apache 2.0").url("http://springdoc.org"))
						.description("API para buscar y gestionar libros"));
	}

}
