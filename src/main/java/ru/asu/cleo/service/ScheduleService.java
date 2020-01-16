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

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public List<String> getFreeTime(ScheduleRequest request) throws ParseException {
        // TODO: 16.01.2020 вынести дату начала и дату конца в конфиг
        Salon salon = salonRepository.findById(request.getSalonId()).orElseThrow();
        Job job = jobRepository.findById(request.getJobId()).orElseThrow();

        List<Time> busyTime = timeRepository.findAllBySalonAndJobAndDate(salon, job, request.getDate().toInstant());
        List<Date> schedule = getSchedule(request.getDate(), "09:00", "20:00");

        for (Iterator<Date> iterator = schedule.iterator(); iterator.hasNext(); ) {
            Date date = iterator.next();
            for (Time time : busyTime) {
                if (date.toInstant().equals(time.getDate())) {

                    iterator.remove();
                    time.getDuration();
                }
            }
        }

        return new ArrayList<>();
    }

    private List<Date> getSchedule(Date date, String beginTime, String endTime) throws ParseException {
        // TODO: 16.01.2020 отрефакторить!
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        List<Date> result = new ArrayList<>();

        String format = dateFormat.format(date);

        beginTime = MessageFormat.format("{0} {1}:00", format, beginTime);
        endTime = MessageFormat.format("{0} {1}:00", format, endTime);
        Date beginInst = simpleDateFormat.parse(beginTime);
        Date endInst = simpleDateFormat.parse(endTime);

        // Создание списка дат с интервалом в пол часа
        Calendar instance = Calendar.getInstance();
        instance.setTime(beginInst);
        while (instance.getTime().before(endInst)) {
            result.add(instance.getTime());
            instance.add(Calendar.MINUTE, 30);
        }

        return result;

    }
}
