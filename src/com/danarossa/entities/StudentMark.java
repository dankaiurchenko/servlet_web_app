package com.danarossa.entities;

import java.io.Serializable;

public class StudentMark extends Entity<Long> implements Serializable {

    private Student student;
    private RealizedCourse realizedCourse;
    private Double mark;

    public StudentMark(Long id, Student student, RealizedCourse realizedCourse, Double mark) {
        super(id);
        this.student = student;
        this.realizedCourse = realizedCourse;
        this.mark = mark;
    }

    public Student getStudent() {
        return student;
    }

    public RealizedCourse getRealizedCourse() {
        return realizedCourse;
    }

    public Double getMark() {
        return mark;
    }
}
