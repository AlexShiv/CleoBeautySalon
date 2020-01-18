package ru.asu.cleo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.asu.cleo.service.ScheduleService;
import ru.asu.cleo.service.dto.ScheduleRequest;
import ru.asu.cleo.service.dto.ScheduleResponse;
import ru.asu.cleo.service.dto.TimeRequest;

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
    public ResponseEntity<ScheduleResponse> getSchedule(@RequestBody ScheduleRequest request) throws ParseException {
        return ResponseEntity.ok(new ScheduleResponse(scheduleService.getFreeTime(request)));
    }

    @PutMapping("/schedule")
    public ResponseEntity<HttpStatus> addRowInTime(@RequestBody TimeRequest request) throws ParseException {
        return ResponseEntity.ok(scheduleService.addRowInTime(request));
    }


}
