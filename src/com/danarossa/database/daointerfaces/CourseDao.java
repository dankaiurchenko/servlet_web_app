package com.danarossa.database.daointerfaces;

import com.danarossa.entities.Course;

import java.util.List;

public interface CourseDao extends GenericDao<Course, Integer> {

    List<Course> getAllCoursesOfLecturer(Integer lecturerId);

    List<Course> getAllCoursesOfStudent(Integer studentId);


}
