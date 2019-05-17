package com.danarossa.database.daointerfaces;

import com.danarossa.entities.StudentMark;

import java.util.List;

public interface StudentMarkDao extends GenericDao<StudentMark, Integer> {

    List<StudentMark> getStudentMarksForRealizedCourse(Integer realizedCourseId);

    List<StudentMark> getStudentMarksForStudent(Integer studentId);

    StudentMark getStudentMarkForStudentAndRealizedCourse(Integer studentId, Integer realizedCourseId);

}
