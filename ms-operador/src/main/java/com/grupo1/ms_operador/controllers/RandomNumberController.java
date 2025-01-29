package com.grupo1.ms_operador.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RandomNumberController {

    //localhost:8080/ms-operador/random-number
    @GetMapping("/random-number")
    public int getRandomNumber() {
        return (int) (Math.random() * 100);
    }

}
