package com.danarossa.database.daointerfaces;

import com.danarossa.entities.User;

import java.util.List;

public interface IUserDao extends GenericDao<User, Long> {
    List<User> getAllLecturersForStudent(Long studentId);
}
