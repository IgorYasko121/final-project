package com.igoryasko.justmusic.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

/**
 * Entity for database table users.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends Entity {

    private static final long serialVersionUID = -2558970303784010499L;

    private long userId;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private LocalDate createdAt;
    private String email;
    private Role role;

    public User(String firstName, String lastName, String login, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.email = email;
        role = Role.USER;
    }

    public enum Role {
        USER, ADMIN, GUEST
    }

}
