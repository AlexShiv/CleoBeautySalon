package ru.asu.cleo.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class ScheduleRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING,
        pattern = "dd-MM-yyyy hh:mm:ss")
    private Date date;
    private List<Double> durations;
    private Long jobId;
    private Long salonId;

    public ScheduleRequest() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Double> getDurations() {
        return durations;
    }

    public void setDurations(List<Double> durations) {
        this.durations = durations;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getSalonId() {
        return salonId;
    }

    public void setSalonId(Long salonId) {
        this.salonId = salonId;
    }
}
