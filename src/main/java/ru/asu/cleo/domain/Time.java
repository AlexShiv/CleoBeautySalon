package ru.asu.cleo.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * Запись
 */
@ApiModel(description = "Запись")
@Entity
@Table(name = "time")
public class Time implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private Instant date;

    @Column(name = "duration")
    private Double duration;

    @ManyToOne
    @JsonIgnoreProperties("times")
    private Client client;

    @ManyToOne
    @JsonIgnoreProperties("times")
    private Job job;

    @ManyToOne
    @JsonIgnoreProperties("times")
    private Salon salon;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public Time date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getDuration() {
        return duration;
    }

    public Time duration(Double duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Client getClient() {
        return client;
    }

    public Time client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Job getJob() {
        return job;
    }

    public Time job(Job job) {
        this.job = job;
        return this;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Salon getSalon() {
        return salon;
    }

    public Time salon(Salon salon) {
        this.salon = salon;
        return this;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Time)) {
            return false;
        }
        return id != null && id.equals(((Time) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Time{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", duration=" + getDuration() +
            "}";
    }
}
