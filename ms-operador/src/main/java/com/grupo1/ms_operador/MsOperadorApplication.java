package com.grupo1.ms_operador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.OpenAPI;

@SpringBootApplication
@EnableFeignClients
public class MsOperadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsOperadorApplication.class, args);
	}
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new io.swagger.v3.oas.models.info.Info()
						.title("Operador API")
						.termsOfService("http://swagger.io/terms/")
						.version("1.0")
						.license(new io.swagger.v3.oas.models.info.License().name("Apache 2.0").url("http://springdoc.org"))
						.description("API para gestionar compras de libros"));
	}
}


