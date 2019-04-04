package com.danarossa.entities;

import java.io.Serializable;
import java.util.Date;

public class Lecturer extends User implements Serializable {
    private String position;
    private Date hireDate;

    public Lecturer(Long id, String name, String surname, Date birthday, String position, Date hireDate) {
        super(id, name, surname, birthday);
        this.position = position;
        this.hireDate = hireDate;
    }

    public String getPosition() {
        return position;
    }

    public Date getHireDate() {
        return hireDate;
    }
}
