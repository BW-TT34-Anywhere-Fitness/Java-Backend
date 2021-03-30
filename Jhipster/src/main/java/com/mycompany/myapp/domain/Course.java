package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.repository.CourseRepository;
import java.io.Serializable;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

/**
 * A Course.
 */
//@EntityListeners({CourseJpaCallbacksListener.class})
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

    @Column(name = "duration")
    private Duration duration;

    @Column(name = "intensity")
    private Integer intensity;

    @Column(name = "location")
    private String location;

    @Column(name = "attenndees")
    private Integer attenndees;

    @Column(name = "maxsize")
    private Integer maxsize;

    @Column(name = "starttime")
    private ZonedDateTime starttime;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "activated", "langKey", "imageUrl", "resetDate" }, allowSetters = true)
    @JoinTable(name = "rel_course__user", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "activated", "langKey", "imageUrl", "resetDate" }, allowSetters = true)
    private User instructor;

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

    public Duration getDuration() {
        return this.duration;
    }

    public Course duration(Duration duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Duration duration) {
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

    public Integer getAttenndees() {
        setAttenndees(this.users.size());
        return this.attenndees;
    }

    public Course attenndees(Integer attenndees) {
        this.attenndees = attenndees;
        return this;
    }

    public void setAttenndees(Integer attenndees) {
        this.attenndees = attenndees;
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

    public ZonedDateTime getStarttime() {
        return this.starttime;
    }

    public Course starttime(ZonedDateTime starttime) {
        this.starttime = starttime;
        return this;
    }

    public void setStarttime(ZonedDateTime starttime) {
        this.starttime = starttime;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public Course users(Set<User> users) {
        this.setUsers(users);
        return this;
    }

    public Course addUser(User user) {
        this.users.add(user);
        return this;
    }

    public Course removeUser(User user) {
        this.users.remove(user);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public User getInstructor() {
        return this.instructor;
    }

    public Course instructor(User user) {
        this.setInstructor(user);
        return this;
    }

    public void setInstructor(User user) {
        this.instructor = user;
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
            ", duration='" + getDuration() + "'" +
            ", intensity=" + getIntensity() +
            ", location='" + getLocation() + "'" +
            ", attenndees=" + getAttenndees() +
            ", maxsize=" + getMaxsize() +
            ", starttime='" + getStarttime() + "'" +
            "}";
    }
}
//@Component
//class CourseJpaCallbacksListener {
//    @Autowired
//    private CourseRepository courseRepository;
//
//    @PreUpdate
//    void preUpdate(Course course) {
//        course.setAttenndees(courseRepository.getAttendeesNumByCourseID(course.getId()));
//    }
//
//}
