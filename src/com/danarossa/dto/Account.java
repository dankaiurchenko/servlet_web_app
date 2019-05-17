package com.danarossa.dto;

import com.danarossa.router.Role;
import lombok.Data;

import java.util.Date;

@Data
public class Account {
    private String name;
    private String surname;
    private String email;
//    private Date dateEntered;
    private Role role;
    private String password;

    public Account(String name, String surname, String email, Date dateEntered, Role role, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
//        this.dateEntered = dateEntered;
        this.role = role;
        this.password = password;
    }

    public Account(String name, String surname, String email, Role role, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
//        this.dateEntered = dateEntered;
        this.role = role;
        this.password = password;
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

//    public Date getDateEntered() {
//        return dateEntered;
//    }

    public Role getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public void setDateEntered(Date dateEntered) {
//        this.dateEntered = dateEntered;
//    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setRole(String  role) {
        this.role = Role.valueOf(role);
    }

    public Account() {
    }


    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                '}';
    }
}
