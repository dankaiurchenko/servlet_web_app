package com.danarossa.entities;

import com.danarossa.router.Role;
import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends Entity<Integer> {
    private String name;
    private String surname;
    private String email;
    @Expose(serialize = false)
    private String password;
    private Date dateEntered;
    private Role role;
    private String token;

    public User(Integer id, String name, String surname, String email, String password, Date dateEntered, Role role) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.dateEntered = dateEntered;
        this.role = role;
    }

    public User(String name, String surname, String email, Date dateEntered, Role role, String token) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.dateEntered = dateEntered;
        this.role = role;
        this.token = token;
    }

    public User(String name, String surname, String email, String password, Date dateEntered, Role role, String token) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.dateEntered = dateEntered;
        this.role = role;
        this.token = token;
    }


}
