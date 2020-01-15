package ru.asu.cleo.domain;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Салоны
 */
@ApiModel(description = "Салоны")
@Entity
@Table(name = "salon")
public class Salon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "salon")
    private Set<Time> times = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public Salon address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public Salon phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Time> getTimes() {
        return times;
    }

    public Salon times(Set<Time> times) {
        this.times = times;
        return this;
    }

    public Salon addTime(Time time) {
        this.times.add(time);
        time.setSalon(this);
        return this;
    }

    public Salon removeTime(Time time) {
        this.times.remove(time);
        time.setSalon(null);
        return this;
    }

    public void setTimes(Set<Time> times) {
        this.times = times;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Salon)) {
            return false;
        }
        return id != null && id.equals(((Salon) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Salon{" +
            "id=" + getId() +
            ", address='" + getAddress() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
