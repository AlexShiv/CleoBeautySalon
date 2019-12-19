package ru.asu.cleo.domain;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность должности сотрудника.
 */
@ApiModel(description = "Сущность должности сотрудника.")
@Entity
@Table(name = "job")
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_title")
    private String jobTitle;

    @OneToMany(mappedBy = "job")
    private Set<Service> services = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Job jobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Set<Service> getServices() {
        return services;
    }

    public Job services(Set<Service> services) {
        this.services = services;
        return this;
    }

    public Job addService(Service service) {
        this.services.add(service);
        service.setJob(this);
        return this;
    }

    public Job removeService(Service service) {
        this.services.remove(service);
        service.setJob(null);
        return this;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Job)) {
            return false;
        }
        return id != null && id.equals(((Job) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Job{" +
            "id=" + getId() +
            ", jobTitle='" + getJobTitle() + "'" +
            "}";
    }
}
