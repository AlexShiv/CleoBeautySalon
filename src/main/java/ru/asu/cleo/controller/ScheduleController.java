package ru.asu.cleo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.asu.cleo.service.ScheduleService;
import ru.asu.cleo.service.dto.ScheduleRequest;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final Logger log = LoggerFactory.getLogger(ScheduleController.class);

    private ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedule")
    public ResponseEntity<List<Date>> getSchedule(@RequestBody ScheduleRequest request) throws ParseException {
        return ResponseEntity.ok(scheduleService.getFreeTime(request));
    }
}
