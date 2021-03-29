package backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Time;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Objects;


@Entity
@Table(name = "course")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Course extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @ManyToOne
    @JoinColumn(name = "id")
    @JsonIgnoreProperties(value = "course", allowSetters = true)
    private User user;

    //    - `Name`
    @Size(max = 100)
    @Unique
    @Column(name = "name", length = 100)
    private String name;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long courseId;


    // - `Type`
    @Size(max = 40)
    @Column(name = "type", length = 40)
    private String type;


    //    - `Start time`
    @Size(max = 40)
    @Column(name = "starttime", length = 40)
    private OffsetDateTime starttime;

    //    - `Duration`
    //    - `Intensity level`
    //    - `Location`
    //    - `Current number of registered attendees`
    //    - `Max class size`

    public Course() {
    }

    public Course(User user, @Size(max = 100) @Unique String name) {
        this.user = user;
        this.name = name;
    }


    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return name != null && name.equals(((Course) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Course{" +
            "name='" + name + '\'' +
            "}";
    }
}
