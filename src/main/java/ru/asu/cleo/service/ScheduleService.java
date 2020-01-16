package ru.asu.cleo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.asu.cleo.domain.Job;
import ru.asu.cleo.domain.Salon;
import ru.asu.cleo.domain.Time;
import ru.asu.cleo.repository.JobRepository;
import ru.asu.cleo.repository.SalonRepository;
import ru.asu.cleo.repository.TimeRepository;
import ru.asu.cleo.service.dto.ScheduleRequest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleService {

    private final SalonRepository salonRepository;
    private final TimeRepository timeRepository;
    private final JobRepository jobRepository;

    @Autowired
    public ScheduleService(SalonRepository salonRepository, TimeRepository timeRepository, JobRepository jobRepository) {
        this.salonRepository = salonRepository;
        this.timeRepository = timeRepository;
        this.jobRepository = jobRepository;
    }

    public List<String> getSchedule(ScheduleRequest request) {
        Salon salon = salonRepository.findById(request.getSalonId()).orElseThrow();
        Job job = jobRepository.findById(request.getJobId()).orElseThrow();

        List<Time> freeTime = timeRepository.findAllBySalonAndJobAndDate(salon, job, request.getDate().toInstant());
        return new ArrayList<>();
    }
}
