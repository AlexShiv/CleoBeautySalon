package ru.asu.cleo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.asu.cleo.service.dto.MainResponseDTO;

@RestController
@RequestMapping("/api")
public class HelloWorldController {

    @GetMapping("/helloWorld")
    private ResponseEntity<MainResponseDTO<String>> getHelloWorld() {
        return ResponseEntity.ok(new MainResponseDTO<>("Leonid - Papyrus"));
    }
}
