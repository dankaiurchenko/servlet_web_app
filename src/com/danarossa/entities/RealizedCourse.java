package com.danarossa.entities;

import java.io.Serializable;
import java.util.Date;

public class RealizedCourse extends Entity<Integer> implements Serializable {
    private Integer courseId;
    private Date startDate;
    private Date endDate;
    private Date examDate;
    private String status;

    public RealizedCourse(Integer courseId, Date startDate, Date endDate, Date examDate, String status) {
        this.courseId = courseId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.examDate = examDate;
        this.status = status;
    }

    public RealizedCourse(Integer id, Integer courseId, Date startDate, Date endDate, Date examDate, String status) {
        super(id);
        this.courseId = courseId;
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

    public Integer getCourseId() {
        return courseId;
    }

    @Override
    public String toString() {
        return "RealizedCourse{" +
                "courseId=" + courseId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", examDate=" + examDate +
                ", status='" + status + '\'' +
                ", id=" + id +
                '}';
    }
}
