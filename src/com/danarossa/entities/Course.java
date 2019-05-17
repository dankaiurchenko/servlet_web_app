package com.danarossa.entities;

import java.io.Serializable;

public class Course extends Entity<Integer> implements Serializable {
    private String title;
    private Integer numberOfHours;
    private Integer hoursForLectures;
    private Integer hoursForPractice;
    private Integer lecturerId;

    public Course(String title,  Integer numberOfHours, Integer hoursForLectures, Integer hoursForPractice, Integer lecturerId) {
        this.title = title;
        this.numberOfHours = numberOfHours;
        this.hoursForLectures = hoursForLectures;
        this.hoursForPractice = hoursForPractice;
        this.lecturerId = lecturerId;
    }

    public Course(Integer id, String title, Integer numberOfHours, Integer hoursForLectures, Integer hoursForPractice,Integer lecturerId) {
        super(id);
        this.title = title;
        this.numberOfHours = numberOfHours;
        this.hoursForLectures = hoursForLectures;
        this.hoursForPractice = hoursForPractice;
        this.lecturerId = lecturerId;
    }

    public String getTitle() {
        return title;
    }

    public Integer getNumberOfHours() {
        return numberOfHours;
    }

    public Integer getHoursForLectures() {
        return hoursForLectures;
    }

    public Integer getHoursForPractice() {
        return hoursForPractice;
    }
    
    public Integer getLecturerId() {
        return lecturerId;
    }

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", numberOfHours=" + numberOfHours +
                ", hoursForLectures=" + hoursForLectures +
                ", hoursForPractice=" + hoursForPractice +
                ", lecturerId=" + lecturerId +
                ", id=" + id +
                '}';
    }
}
