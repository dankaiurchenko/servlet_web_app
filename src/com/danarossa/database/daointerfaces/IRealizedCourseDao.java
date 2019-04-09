package com.danarossa.database.daointerfaces;

import com.danarossa.entities.RealizedCourse;

import java.util.List;

public interface IRealizedCourseDao extends GenericDao<RealizedCourse, Long> {
    List<RealizedCourse> getAllRealizedCoursesOfLecturer(Long lecturerId);

    List<RealizedCourse> getAllRealizedCoursesOfStudent(Long studentId);

    List<RealizedCourse> getAllRealizedCoursesOfCourse(Long courseId);
}
