package com.grupo1.ms_operador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsOperadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsOperadorApplication.class, args);
	}
}


