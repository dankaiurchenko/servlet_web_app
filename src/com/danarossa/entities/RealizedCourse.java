package com.danarossa.entities;

import java.io.Serializable;
import java.util.Date;

public class RealizedCourse extends Entity<Long> implements Serializable {
    private Course course;
    private Date startDate;
    private Date endDate;
    private Date examDate;
    private String status;

    public RealizedCourse(Long id, Course course, Date startDate, Date endDate, Date examDate, String status) {
        super(id);
        this.course = course;
        this.startDate = startDate;
        this.endDate = endDate;
        this.examDate = examDate;
        this.status = status;
    }

    public Course getCourse() {
        return course;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getExamDate() {
        return examDate;
    }

    public String getStatus() {
        return status;
    }
}
