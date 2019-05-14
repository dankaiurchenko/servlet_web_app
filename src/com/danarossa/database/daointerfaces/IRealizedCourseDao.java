package com.danarossa.database.daointerfaces;

import com.danarossa.entities.RealizedCourse;

import java.util.List;

public interface IRealizedCourseDao extends GenericDao<RealizedCourse, Integer> {
    List<RealizedCourse> getAllRealizedCoursesOfLecturer(Integer lecturerId);

    List<RealizedCourse> getAllRealizedCoursesOfStudent(Integer studentId);

    List<RealizedCourse> getAllRealizedCoursesOfCourse(Integer courseId);
}
