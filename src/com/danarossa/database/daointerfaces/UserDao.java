package com.danarossa.database.daointerfaces;

import com.danarossa.entities.User;

import java.util.List;

public interface UserDao extends GenericDao<User, Integer> {
    List<User> getAllLecturersForStudent(Integer studentId);

    User getUserByToken(String token);
}
