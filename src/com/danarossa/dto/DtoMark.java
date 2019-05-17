package com.danarossa.dto;

public class DtoMark {
    private Integer studentId;
    private Integer realizedCourseId;
    private Double mark;

    public DtoMark() {
    }

    public DtoMark(Integer studentId, Integer realizedCourseId, Double mark) {
        this.studentId = studentId;
        this.realizedCourseId = realizedCourseId;
        this.mark = mark;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getRealizedCourseId() {
        return realizedCourseId;
    }

    public void setRealizedCourseId(Integer realizedCourseId) {
        this.realizedCourseId = realizedCourseId;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }
}
