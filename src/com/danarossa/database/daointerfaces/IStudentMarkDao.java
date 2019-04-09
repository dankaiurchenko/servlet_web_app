package com.danarossa.database.daointerfaces;

import com.danarossa.entities.StudentMark;

import java.util.List;

public interface IStudentMarkDao extends GenericDao<StudentMark, Long> {

    List<StudentMark> getStudentMarksForRealizedCourse(Long realizedCourseId);

    List<StudentMark> getStudentMarksForStudent(Long studentId);

}
