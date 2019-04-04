package com.danarossa.entities;

import java.io.Serializable;

public class Course extends Entity<Long> implements Serializable {
    private String title;
    private int numberOfCredits;
    private int numberOfHours;
    private int hoursForLectures;
    private int hoursForPractice;
    private int hoursForHomeStudy;
    private Lecturer lecturer;

    public Course(Long id, String title, int numberOfCredits, int numberOfHours, int hoursForLectures, int hoursForPractice, int hoursForHomeStudy, Lecturer lecturer) {
        super(id);
        this.title = title;
        this.numberOfCredits = numberOfCredits;
        this.numberOfHours = numberOfHours;
        this.hoursForLectures = hoursForLectures;
        this.hoursForPractice = hoursForPractice;
        this.hoursForHomeStudy = hoursForHomeStudy;
        this.lecturer = lecturer;
    }

    public String getTitle() {
        return title;
    }

    public int getNumberOfCredits() {
        return numberOfCredits;
    }

    public int getNumberOfHours() {
        return numberOfHours;
    }

    public int getHoursForLectures() {
        return hoursForLectures;
    }

    public int getHoursForPractice() {
        return hoursForPractice;
    }

    public int getHoursForHomeStudy() {
        return hoursForHomeStudy;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public long getLecturerId() {
        return lecturer.getId();
    }
}
