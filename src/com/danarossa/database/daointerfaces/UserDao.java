package com.danarossa.database.daointerfaces;

import com.danarossa.entities.User;
import com.danarossa.router.Role;

import java.util.List;

public interface UserDao extends GenericDao<User, Integer> {
    List<User> getAllLecturersForStudent(Integer studentId);

    List<User> getAllOfRole(Role role);

    List<User> getAllStudentsOfRealizedCourse(Integer realizedCourseId);

    User getUserByToken(String token);

    User getUserByEmail(String email);

    User getUserByEmailAndPass(String email, String password);

}
