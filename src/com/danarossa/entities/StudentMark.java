package com.danarossa.entities;

import java.io.Serializable;
import java.util.Objects;

public class StudentMark extends Entity<Long> implements Serializable {

    private User student;
    private RealizedCourse realizedCourse;
    private Double mark;

    public StudentMark(User student, RealizedCourse realizedCourse, Double mark) {
        this.student = student;
        this.realizedCourse = realizedCourse;
        this.mark = mark;
    }

    public StudentMark(Long id, User student, RealizedCourse realizedCourse, Double mark) {
        super(id);
        this.student = student;
        this.realizedCourse = realizedCourse;
        this.mark = mark;
    }

    public Double getMark() {
        return mark;
    }

    public long getStudentId() {
        return student.getId();
    }

    public long getRealizedCourseId() {
        return realizedCourse.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentMark)) return false;
        if (!super.equals(o)) return false;
        StudentMark that = (StudentMark) o;
        return Objects.equals(student.id, that.student.id) &&
                Objects.equals(realizedCourse.id, that.realizedCourse.id) &&
                Objects.equals(mark, that.mark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), student, realizedCourse, mark);
    }

    @Override
    public String toString() {
        return "StudentMark{  " +
                id + "   " +
                "student=" + student +
                ", realizedCourse=" + realizedCourse +
                ", mark=" + mark +
                '}';
    }
}
