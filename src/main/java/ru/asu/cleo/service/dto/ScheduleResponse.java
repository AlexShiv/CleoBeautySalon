package ru.asu.cleo.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class ScheduleResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING,
        pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Europe/Astrakhan")
    private List<Date> dates;

    public ScheduleResponse(List<Date> dates) {
        this.dates = dates;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }
}
