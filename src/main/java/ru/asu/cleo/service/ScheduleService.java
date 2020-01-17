package ru.asu.cleo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger log = LoggerFactory.getLogger(ScheduleService.class);

    private final SalonRepository salonRepository;
    private final TimeRepository timeRepository;
    private final JobRepository jobRepository;

    @Autowired
    public ScheduleService(SalonRepository salonRepository, TimeRepository timeRepository, JobRepository jobRepository) {
        this.salonRepository = salonRepository;
        this.timeRepository = timeRepository;
        this.jobRepository = jobRepository;
    }

    public List<Date> getFreeTime(ScheduleRequest request) throws ParseException {
        Salon salon = salonRepository.findById(request.getSalonId()).orElseThrow(NoSuchElementException::new);
        Job job = jobRepository.findById(request.getJobId()).orElseThrow(NoSuchElementException::new);
        double sumDurations = request.getDurations().stream().mapToDouble(Double::doubleValue).sum() * 2.0;

        List<Time> busyTime = timeRepository.findAllBySalonAndJobAndDate(salon, job, request.getDate().toInstant());
        // TODO: 16.01.2020 вынести дату начала и дату конца в конфиг
        String beginTime = "09:00";
        String endTime = "20:00";
        List<Date> schedule = getSchedule(request.getDate(), beginTime, endTime);

        // Удаляем уже занятые даты
        for (Iterator<Date> iterator = schedule.iterator(); iterator.hasNext(); ) {
            Date date = iterator.next();
            log.debug(MessageFormat.format("Start deleting time from schedule. Date: {0}", date.toString()));
            for (Time time : busyTime) {
                if (date.toInstant().equals(time.getDate())) {
                    // Удаляем вдвое больше элементов т.к. период пол часа
                    double count = time.getDuration() * 2.0;
                    deleteBusyTime(iterator, time, count);
                    break;
                }
            }
        }

        // Удаляем даты в которые не влезет отправленный промежуток
        List<Date> allTimes = getSchedule(request.getDate(), beginTime, endTime);
        for (Iterator<Date> iterator = schedule.iterator(); iterator.hasNext(); ) {
            Date date = iterator.next();
            int index = allTimes.indexOf(date);
            try {
                List<Date> subList = allTimes.subList(index, (int) (index + sumDurations));
                if (!schedule.containsAll(subList)) {
                    iterator.remove();
                }
            } catch (IndexOutOfBoundsException e) {
                log.info("End of list");
                iterator.remove();
            }
        }

        return schedule;
    }

    private void deleteBusyTime(Iterator<Date> iterator, Time time, double count) {
        for (int i = 0; i < count; i++) {
            // Костыль для того что бы не удалялся самый последний элемент
            if (count - i == 1.0) {
                iterator.remove();
            } else if (iterator.hasNext()) {
                iterator.remove();
                Date next = iterator.next();
                log.debug(MessageFormat.format("Time {0} deleted!", next.toString()));
            } else {
                log.warn(MessageFormat.format("Can't delete time from schedule:{0}! Iterator is empty!", time.getDate().toString()));
            }
        }
    }

    private List<Date> getSchedule(Date date, String beginTime, String endTime) throws ParseException {
        // TODO: 16.01.2020 отрефакторить!
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        List<Date> result = new ArrayList<>();

        String format = dateFormat.format(date);

        beginTime = MessageFormat.format("{0} {1}:00", format, beginTime);
        endTime = MessageFormat.format("{0} {1}:00", format, endTime);
        Date beginInst = simpleDateFormat.parse(beginTime);
        Date endInst = simpleDateFormat.parse(endTime);

        // Создание списка дат с интервалом в пол часа
        Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
        instance.setTime(beginInst);
        while (instance.getTime().before(endInst)) {
            result.add(instance.getTime());
            instance.add(Calendar.MINUTE, 30);
        }

        return result;
    }
}
