package com.hospitalmanagement.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "https://mahizham-hosp.vercel.app/")
public class RootController {

    @GetMapping("/")
    public String home() {
        return "Mahizham Hospital Backend Running";
    }
}