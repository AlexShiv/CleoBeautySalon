package ru.asu.cleo.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.asu.cleo.domain.Service;
import ru.asu.cleo.repository.ServiceRepository;
import ru.asu.cleo.service.dto.MainResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/helloWorld")
public class HelloWorldController {

    private final ServiceRepository serviceRepository;

    public HelloWorldController(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @GetMapping("")
    private ResponseEntity<MainResponse<String>> getHelloWorld() {
        return ResponseEntity.ok(new MainResponse<>("Leonid - Papyrus"));
    }

    @GetMapping("/listObject")
    private ResponseEntity<List<Test>> getHelloWorldListObject() {
        List<Test> tests = new ArrayList<>();
        Test s1 = new Test();
        s1.setId(1L);
        s1.setName("Маникюр");
        s1.setDuration(3);

        Test s2 = new Test();
        s2.setId(2L);
        s2.setName("Педикюр");
        s2.setDuration(5);
        tests.add(s1);
        tests.add(s2);
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/object")
    private ResponseEntity<Service> getHelloWorldObject() {
        Service employee = serviceRepository.findById(1L).get();
        return ResponseEntity.ok(employee);
    }

    @PostMapping
    private ResponseEntity sendTestData(@RequestBody Test test) {
        if (test.duration<5) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/listObject")
    private ResponseEntity sendTestData(@RequestBody List<Test> test) {
        for (Test test1 : test) {
            if (test1.duration>=5) {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.ok().build();
    }

    private static class Test {
        @JsonProperty("id")
        private Long id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("duration")
        private Integer duration;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }
    }
}
