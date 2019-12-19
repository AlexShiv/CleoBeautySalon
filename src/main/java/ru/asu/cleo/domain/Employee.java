package ru.asu.cleo.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;

/**
 * Сущность сотрудника.
 */
@ApiModel(description = "Сущность сотрудника.")
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "patronomic")
    private String patronomic;

    @Column(name = "experience")
    private String experience;

    @ManyToOne
    @JsonIgnoreProperties("employees")
    private Job job;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Employee firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronomic() {
        return patronomic;
    }

    public Employee patronomic(String patronomic) {
        this.patronomic = patronomic;
        return this;
    }

    public void setPatronomic(String patronomic) {
        this.patronomic = patronomic;
    }

    public String getExperience() {
        return experience;
    }

    public Employee experience(String experience) {
        this.experience = experience;
        return this;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public Job getJob() {
        return job;
    }

    public Employee job(Job job) {
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
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", patronomic='" + getPatronomic() + "'" +
            ", experience='" + getExperience() + "'" +
            "}";
    }
}
