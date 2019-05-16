package com.danarossa.entities;

import com.danarossa.router.Role;

import java.util.Date;
import java.util.Objects;

public class User extends Entity<Integer> {
    private String name;
    private String surname;
    private final String email;
    private final String password;
    private final Date dateEntered;
    private final Role role;

    public User(String name, String surname, String email, String password, Date dateEntered, Role role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.dateEntered = dateEntered;
        this.role = role;
    }


    public User(Integer id, String name, String surname, String email, String password, Date dateEntered, Role role) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.dateEntered = dateEntered;
        this.role = role;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(dateEntered, user.dateEntered) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, surname, email, password, dateEntered, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dateEntered=" + dateEntered +
                ", role='" + role + '\'' +
                ", id=" + id +
                '}';
    }
}
