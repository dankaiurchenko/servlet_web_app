package com.danarossa.database.daointerfaces;

import com.danarossa.entities.Lecturer;

import java.util.List;

public interface ILecturerDao extends GenericDao<Lecturer, Long> {

    List<Lecturer> getAllLecturersForStudent(Long studentId);
}
