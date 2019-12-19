package ru.asu.cleo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloWorldController {

    @GetMapping("/helloWorld")
    private ResponseEntity<String> getHelloWorld() {
        return ResponseEntity.ok("Hello world");
    }
}
