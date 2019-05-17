package com.danarossa.entities;

import java.io.Serializable;

public class StudentMark extends Entity<Integer> implements Serializable {

    private User student;
    private Integer realizedCourseId;
    private Double mark;

    public StudentMark(User student, Integer realizedCourse, Double mark) {
        this.student = student;
        this.realizedCourseId = realizedCourse;
        this.mark = mark;
    }

    public StudentMark(Integer id, User student, Integer realizedCourse, Double mark) {
        super(id);
        this.student = student;
        this.realizedCourseId = realizedCourse;
        this.mark = mark;
    }

    public Double getMark() {
        return mark;
    }

    public Integer getStudentId() {
        return student.getId();
    }

    public User getStudent() {
        return student;
    }

    public Integer getRealizedCourseId() {
        return realizedCourseId;
    }

    @Override
    public String toString() {
        return "StudentMark{" +
                "student=" + student +
                ", realizedCourseId=" + realizedCourseId +
                ", mark=" + mark +
                ", id=" + id +
                '}';
    }
}
