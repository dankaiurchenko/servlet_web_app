package com.danarossa.entities;

import java.util.Date;

public class User extends Entity<Long> {
    private String name = "";
    private String surname = "";
    private Date birthday = new Date();

    public User(Long id) {
        super(id);
    }

    public User(Long id, String name, String surname, Date birthday) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirthday() {
        return birthday;
    }

}
