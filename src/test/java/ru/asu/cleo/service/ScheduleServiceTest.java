package ru.asu.cleo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import ru.asu.cleo.CleoBeautySalonApp;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = CleoBeautySalonApp.class)
class ScheduleServiceTest {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private TimeRepository timeRepository;
    @Autowired
    private SalonRepository salonRepository;
    @Autowired
    private JobRepository jobRepository;

    @Test
    void getSchedule() {
//        Instant instant = Instant.now().s;
//        System.out.println(instant.toString());

        Salon salon = salonRepository.findById(1L).orElseThrow(NoSuchElementException::new);
        Job job = jobRepository.findById(1L).orElseThrow(NoSuchElementException::new);



        ScheduleRequest scheduleRequest = new ScheduleRequest();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        try {
            Date date = dateFormat.parse("17-01-2020 10:00:00");
            scheduleRequest.setDate(date);
            scheduleRequest.setDurations(Arrays.asList(1.5, 2.0));
            scheduleRequest.setJobId(1L);
            scheduleRequest.setSalonId(1L);

            List<Time> freeTime = timeRepository.findAllBySalonAndJobAndDate(salon, job, date.toInstant());
            System.out.println(freeTime);

//            List<String> actualFreeTime = scheduleService.getSchedule(scheduleRequest);

//            assertThat(actualFreeTime).isEqualTo(Arrays.asList("09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
