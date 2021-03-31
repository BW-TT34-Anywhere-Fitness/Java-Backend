package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Userextra.
 */
@Entity
@Table(name = "userextra")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Userextra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "accountype")
    private String accountype;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonIgnore
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Userextra id(Long id) {
        this.id = id;
        return this;
    }

    public String getAccountype() {
        return this.accountype;
    }

    public Userextra accountype(String accountype) {
        this.accountype = accountype;
        return this;
    }

    public void setAccountype(String accountype) {
        this.accountype = accountype;
    }

    public User getUser() {
        return this.user;
    }

    public Userextra user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Userextra)) {
            return false;
        }
        return id != null && id.equals(((Userextra) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Userextra{" +
            "id=" + getId() +
            ", accountype='" + getAccountype() + "'" +
            "}";
    }
}
