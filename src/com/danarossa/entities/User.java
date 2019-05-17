package com.danarossa.entities;

import com.danarossa.router.Role;
import com.google.gson.annotations.Expose;
import lombok.Data;

import java.util.Date;

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


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Date getDateEntered() {
        return dateEntered;
    }

    public Role getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateEntered(Date dateEntered) {
        this.dateEntered = dateEntered;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
