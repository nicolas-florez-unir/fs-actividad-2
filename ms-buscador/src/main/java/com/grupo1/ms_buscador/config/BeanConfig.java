package com.grupo1.ms_buscador.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeanConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
