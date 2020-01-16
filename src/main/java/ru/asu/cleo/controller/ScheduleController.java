package ru.asu.cleo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.asu.cleo.service.ScheduleService;
import ru.asu.cleo.service.dto.ScheduleRequest;
import ru.asu.cleo.web.rest.ClientResource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final Logger log = LoggerFactory.getLogger(ClientResource.class);

    private ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedule")
    public ResponseEntity<List<String>> getSchedule(@RequestBody ScheduleRequest request) {
        return ResponseEntity.ok(scheduleService.getSchedule(request));
    }
}
