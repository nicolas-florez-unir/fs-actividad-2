package com.grupo1.ms_buscador.controllers;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RandomNameController {
    @GetMapping("/random-name")
    public String hello() {
        ArrayList<String> names = new ArrayList<String>() {
            {
                add("Juan");
                add("Pedro");
                add("Pablo");
                add("Maria");
                add("Jose");
                add("Ana");
                add("Luis");
                add("Carlos");
                add("Fernando");
                add("Ricardo");
                add("Sofia");
                add("Laura");
                add("Carmen");
                add("Rosa");
                add("Antonio");
                add("Javier");
                add("Miguel");
                add("Raul");
                add("Eduardo");
                add("Roberto");
            }
        };

        return names.get((int) (Math.random() * names.size()));
    }
}