package ru.asu.cleo.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Service.
 */
@Entity
@Table(name = "service")
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "max_duration")
    private Integer maxDuration;

    @Column(name = "price")
    private Integer price;

    @OneToMany(mappedBy = "service")
    private Set<Time> times = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("services")
    private Job job;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Service serviceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getMaxDuration() {
        return maxDuration;
    }

    public Service maxDuration(Integer maxDuration) {
        this.maxDuration = maxDuration;
        return this;
    }

    public void setMaxDuration(Integer maxDuration) {
        this.maxDuration = maxDuration;
    }

    public Integer getPrice() {
        return price;
    }

    public Service price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Set<Time> getTimes() {
        return times;
    }

    public Service times(Set<Time> times) {
        this.times = times;
        return this;
    }

    public Service addTime(Time time) {
        this.times.add(time);
        time.setService(this);
        return this;
    }

    public Service removeTime(Time time) {
        this.times.remove(time);
        time.setService(null);
        return this;
    }

    public void setTimes(Set<Time> times) {
        this.times = times;
    }

    public Job getJob() {
        return job;
    }

    public Service job(Job job) {
        this.job = job;
        return this;
    }

    public void setJob(Job job) {
        this.job = job;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Service)) {
            return false;
        }
        return id != null && id.equals(((Service) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Service{" +
            "id=" + getId() +
            ", serviceName='" + getServiceName() + "'" +
            ", maxDuration=" + getMaxDuration() +
            ", price=" + getPrice() +
            "}";
    }
}
