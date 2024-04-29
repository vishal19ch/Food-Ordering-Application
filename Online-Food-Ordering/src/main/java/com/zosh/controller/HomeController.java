package com.zosh.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class HomeController {
    @GetMapping
    public ResponseEntity<String> HomeController() {
        return new ResponseEntity<>("Welcome to food Delivery...", HttpStatus.OK);
    }
}
