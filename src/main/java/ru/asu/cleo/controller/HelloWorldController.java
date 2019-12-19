package ru.asu.cleo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.asu.cleo.domain.Client;
import ru.asu.cleo.domain.Employee;
import ru.asu.cleo.repository.EmployeeRepository;
import ru.asu.cleo.service.dto.MainListResponse;
import ru.asu.cleo.service.dto.MainResponse;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/helloWorld")
public class HelloWorldController {

    private final EmployeeRepository employeeRepository;

    public HelloWorldController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @GetMapping("")
    private ResponseEntity<MainResponse<String>> getHelloWorld() {
        return ResponseEntity.ok(new MainResponse<>("Leonid - Papyrus"));
    }

    @GetMapping("/list")
    private ResponseEntity<MainListResponse<String>> getHelloWorldList() {
        List<String> strings = Arrays.asList("Lenya", "Nata", "Tanya", "Lesha");
        return ResponseEntity.ok(new MainListResponse<>(strings));
    }

    @GetMapping("/listObject")
    private ResponseEntity<MainListResponse<Employee>> getHelloWorldListObject() {
        return ResponseEntity.ok(new MainListResponse<>(employeeRepository.findAll()));
    }

    @GetMapping("/object")
    private ResponseEntity<MainResponse<Employee>> getHelloWorldObject() {
        Employee employee = employeeRepository.findById(1L).get();
        return ResponseEntity.ok(new MainResponse<>(employee));
    }
}
