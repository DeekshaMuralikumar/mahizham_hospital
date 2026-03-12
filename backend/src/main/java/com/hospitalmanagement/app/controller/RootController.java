package com.hospitalmanagement.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
public class RootController {

    @GetMapping("/")
    public String home() {
        return "Mahizham Hospital Backend Running";
    }
}