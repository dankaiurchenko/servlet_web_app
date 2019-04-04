package com.danarossa.entities;

import java.io.Serializable;
import java.util.Date;

public class Student extends User implements Serializable {

    private Date dateEntered;

    public Student(Long id, String name, String surname, Date birthday, Date dateEntered) {
        super(id, name, surname, birthday);
        this.dateEntered = dateEntered;
    }

    public Date getDateEntered() {
        return dateEntered;
    }
}
