package com.myapp.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "starttime")
    private LocalDate starttime;

    @Column(name = "duration")
    private String duration;

    @Column(name = "intensity")
    private Integer intensity;

    @Column(name = "location")
    private String location;

    @Column(name = "currentsize")
    private Integer currentsize;

    @Column(name = "maxsize")
    private Integer maxsize;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Course name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public Course type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getStarttime() {
        return this.starttime;
    }

    public Course starttime(LocalDate starttime) {
        this.starttime = starttime;
        return this;
    }

    public void setStarttime(LocalDate starttime) {
        this.starttime = starttime;
    }

    public String getDuration() {
        return this.duration;
    }

    public Course duration(String duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getIntensity() {
        return this.intensity;
    }

    public Course intensity(Integer intensity) {
        this.intensity = intensity;
        return this;
    }

    public void setIntensity(Integer intensity) {
        this.intensity = intensity;
    }

    public String getLocation() {
        return this.location;
    }

    public Course location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCurrentsize() {
        return this.currentsize;
    }

    public Course currentsize(Integer currentsize) {
        this.currentsize = currentsize;
        return this;
    }

    public void setCurrentsize(Integer currentsize) {
        this.currentsize = currentsize;
    }

    public Integer getMaxsize() {
        return this.maxsize;
    }

    public Course maxsize(Integer maxsize) {
        this.maxsize = maxsize;
        return this;
    }

    public void setMaxsize(Integer maxsize) {
        this.maxsize = maxsize;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        return id != null && id.equals(((Course) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", starttime='" + getStarttime() + "'" +
            ", duration='" + getDuration() + "'" +
            ", intensity=" + getIntensity() +
            ", location='" + getLocation() + "'" +
            ", currentsize=" + getCurrentsize() +
            ", maxsize=" + getMaxsize() +
            "}";
    }
}
