package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 30)
    @Column(name = "title", length = 30, nullable = false)
    private String title;

    @Column(name = "bodytext")
    @Size(max = 500)
    private String bodytext;

    @Column(name = "datetime")
    private ZonedDateTime datetime;

    @ManyToOne
    @JsonIgnoreProperties(value = { "users", "instructor" }, allowSetters = true)
    private Course parentcourse;

    @ManyToOne
    @JsonIgnoreProperties({ "firstName", "lastName", "email", "activated", "langKey", "imageUrl", "resetDate" })
    private User author;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Comment id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Comment title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBodytext() {
        return this.bodytext;
    }

    public Comment bodytext(String bodytext) {
        this.bodytext = bodytext;
        return this;
    }

    public void setBodytext(String bodytext) {
        this.bodytext = bodytext;
    }

    public ZonedDateTime getDatetime() {
        return this.datetime;
    }

    public Comment datetime(ZonedDateTime datetime) {
        this.datetime = datetime;
        return this;
    }

    public void setDatetime(ZonedDateTime datetime) {
        this.datetime = datetime;
    }

    public Course getParentcourse() {
        return this.parentcourse;
    }

    public Comment parentcourse(Course course) {
        this.setParentcourse(course);
        return this;
    }

    public void setParentcourse(Course course) {
        this.parentcourse = course;
    }

    public User getAuthor() {
        return this.author;
    }

    public Comment author(User user) {
        this.setAuthor(user);
        return this;
    }

    public void setAuthor(User user) {
        this.author = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment)) {
            return false;
        }
        return id != null && id.equals(((Comment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", bodytext='" + getBodytext() + "'" +
            ", datetime='" + getDatetime() + "'" +
            "}";
    }
}
