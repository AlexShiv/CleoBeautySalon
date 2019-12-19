package ru.asu.cleo.domain;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * Сущность акций.
 */
@ApiModel(description = "Сущность акций.")
@Entity
@Table(name = "promo")
public class Promo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "promo_name")
    private String promoName;

    @Column(name = "description")
    private Long description;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "sale")
    private Integer sale;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPromoName() {
        return promoName;
    }

    public Promo promoName(String promoName) {
        this.promoName = promoName;
        return this;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public Long getDescription() {
        return description;
    }

    public Promo description(Long description) {
        this.description = description;
        return this;
    }

    public void setDescription(Long description) {
        this.description = description;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Promo startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public Promo endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getSale() {
        return sale;
    }

    public Promo sale(Integer sale) {
        this.sale = sale;
        return this;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Promo)) {
            return false;
        }
        return id != null && id.equals(((Promo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Promo{" +
            "id=" + getId() +
            ", promoName='" + getPromoName() + "'" +
            ", description=" + getDescription() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", sale=" + getSale() +
            "}";
    }
}
