package com.danarossa.entities;

import java.io.Serializable;
import java.util.Objects;

public class Course extends Entity<Long> implements Serializable {
    private String title;
    private int numberOfCredits;
    private int numberOfHours;
    private int hoursForLectures;
    private int hoursForPractice;
    private int hoursForHomeStudy;
    private User lecturer;

    public Course(String title, int numberOfCredits, int numberOfHours, int hoursForLectures, int hoursForPractice, int hoursForHomeStudy, User lecturer) {
        this.title = title;
        this.numberOfCredits = numberOfCredits;
        this.numberOfHours = numberOfHours;
        this.hoursForLectures = hoursForLectures;
        this.hoursForPractice = hoursForPractice;
        this.hoursForHomeStudy = hoursForHomeStudy;
        this.lecturer = lecturer;
    }

    public Course(Long id, String title, int numberOfCredits, int numberOfHours, int hoursForLectures, int hoursForPractice, int hoursForHomeStudy, User lecturer) {
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

    public User getLecturer() {
        return lecturer;
    }

    public long getLecturerId() {
        return lecturer.getId();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNumberOfCredits(int numberOfCredits) {
        this.numberOfCredits = numberOfCredits;
    }

    public void setNumberOfHours(int numberOfHours) {
        this.numberOfHours = numberOfHours;
    }

    public void setHoursForLectures(int hoursForLectures) {
        this.hoursForLectures = hoursForLectures;
    }

    public void setHoursForPractice(int hoursForPractice) {
        this.hoursForPractice = hoursForPractice;
    }

    public void setHoursForHomeStudy(int hoursForHomeStudy) {
        this.hoursForHomeStudy = hoursForHomeStudy;
    }

    public void setLecturer(User lecturer) {
        this.lecturer = lecturer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Course course = (Course) o;
        return numberOfCredits == course.numberOfCredits &&
                numberOfHours == course.numberOfHours &&
                hoursForLectures == course.hoursForLectures &&
                hoursForPractice == course.hoursForPractice &&
                hoursForHomeStudy == course.hoursForHomeStudy &&
                Objects.equals(title, course.title) &&
                Objects.equals(lecturer.id, course.lecturer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, numberOfCredits, numberOfHours, hoursForLectures, hoursForPractice, hoursForHomeStudy, lecturer);
    }
}
