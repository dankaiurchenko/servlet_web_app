package com.danarossa.database.daointerfaces;

import com.danarossa.entities.Course;

import java.util.List;

public interface ICourseDao extends GenericDao<Course, Long> {

    List<Course> getAllCoursesOfLecturer(Long lecturerId);

    List<Course> getAllCoursesOfStudent(Long studentId);


}
