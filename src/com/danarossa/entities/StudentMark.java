package com.danarossa.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
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
}
