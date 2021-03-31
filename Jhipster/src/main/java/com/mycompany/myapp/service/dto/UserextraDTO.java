package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.Userextra;

/**
 * A DTO representing a user, with only the public attributes.
 */
public class UserextraDTO {

    private Long id;

    private String login;

    private User user;

    private String accountype;

    private String firstName;

    private String lastName;

    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserextraDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserextraDTO(Userextra userextra) {
        this.id = userextra.getId();
        // Customize it here if you need, or not, firstName/lastName/etc
        this.user = userextra.getUser();
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.accountype = userextra.getAccountype();
    }

    public UserextraDTO(User user) {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountype() {
        return accountype;
    }

    public void setAccountype(String accountype) {
        this.accountype = accountype;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserextraDTO{" +
            "id='" + id + '\'' +
            ", login='" + login + '\'' +
            ", accounttype='" + login + '\'' +
            "}";
    }
}
