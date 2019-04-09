package com.danarossa.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Lecturer extends User implements Serializable {
    private String position;
    private Date hireDate;

    public Lecturer(String name, String surname, Date birthday, String position, Date hireDate) {
        super(name, surname, birthday);
        this.position = position;
        this.hireDate = hireDate;
    }

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


    public void setPosition(String position) {
        this.position = position;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lecturer)) return false;
        if (!super.equals(o)) return false;
        Lecturer lecturer = (Lecturer) o;
        return Objects.equals(position, lecturer.position) &&
                Objects.equals(hireDate, lecturer.hireDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), position, hireDate);
    }
}
