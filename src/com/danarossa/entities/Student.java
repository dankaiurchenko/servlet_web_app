package com.danarossa.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Student extends User implements Serializable {

    private Date dateEntered;

    public Student(Long id, String name, String surname, Date birthday, Date dateEntered) {
        super(id, name, surname, birthday);
        this.dateEntered = dateEntered;
    }

    public Student(String name, String surname, Date birthday, Date dateEntered) {
        super(name, surname, birthday);
        this.dateEntered = dateEntered;
    }

    public Date getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(Date dateEntered) {
        this.dateEntered = dateEntered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return Objects.equals(dateEntered, student.dateEntered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dateEntered);
    }
}
