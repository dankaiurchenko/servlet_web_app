package com.danarossa.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class RealizedCourse extends Entity<Long> implements Serializable {
    private Course course;
    private Date startDate;
    private Date endDate;
    private Date examDate;
    private String status;

    public RealizedCourse(Course course, Date startDate, Date endDate, Date examDate, String status) {
        this.course = course;
        this.startDate = startDate;
        this.endDate = endDate;
        this.examDate = examDate;
        this.status = status;
    }

    public RealizedCourse(Long id, Course course, Date startDate, Date endDate, Date examDate, String status) {
        super(id);
        this.course = course;
        this.startDate = startDate;
        this.endDate = endDate;
        this.examDate = examDate;
        this.status = status;
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

    public Long getCourseId() {
        return course.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RealizedCourse)) return false;
        if (!super.equals(o)) return false;
        RealizedCourse that = (RealizedCourse) o;
        return Objects.equals(course.id, that.course.id) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(examDate, that.examDate) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), course, startDate, endDate, examDate, status);
    }
}
