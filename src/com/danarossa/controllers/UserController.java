package com.danarossa.controllers;

import com.danarossa.database.daointerfaces.StudentMarkDao;
import com.danarossa.database.daointerfaces.UserDao;
import com.danarossa.dto.DtoMark;
import com.danarossa.dto.StudentWithMark;
import com.danarossa.entities.User;
import com.danarossa.router.Accessible;
import com.danarossa.router.Controller;
import com.danarossa.router.Role;
import com.danarossa.router.Url;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@Controller("/users")
public class UserController extends ParentController {

    @Url("/get-all")
    @Accessible({Role.ADMIN})
    public void all(HttpServletRequest request, HttpServletResponse response) {
        try (UserDao userDao = abstractDaoFactory.getUserDao()) {
            writeToResponseBody(userDao.getAll(), response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("cant get all trainers");
        }
    }

    @Url("/new-one")
    @Accessible(Role.ADMIN)
    public void add(HttpServletRequest request, HttpServletResponse response) {
        try (UserDao userDao = abstractDaoFactory.getUserDao()) {
            System.out.println("into /new-one");
            String body = getBody(request);
            System.out.println("user from front    " + body);
            User newUser = gson.fromJson(body, User.class);
            User userByEmail = userDao.getUserByEmail(newUser.getEmail());
            if (userByEmail == null) {
                userDao.insert(newUser);
                writeToResponseBody(newUser.getId(), response);
            } else throw new ControllerException("User not found");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("Error");
        }
    }

    @Url("/students")
    public void allStudents(HttpServletRequest request, HttpServletResponse response) {
        try (UserDao userDao = abstractDaoFactory.getUserDao()) {
            writeToResponseBody(userDao.getAllOfRole(Role.STUDENT), response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("cant get all trainers");
        }
    }


    @Url("/get-by-course")
    public void allOfCourse(HttpServletRequest request, HttpServletResponse response) {
        try (UserDao userDao = abstractDaoFactory.getUserDao(); StudentMarkDao studentMarkDao = abstractDaoFactory.getStudentMarkDao()) {
            int realizedCourseId = Integer.parseInt(request.getParameter("realizedCourseId"));
            List<User> allStudentsOfRealizedCourse = userDao.getAllStudentsOfRealizedCourse(realizedCourseId);
            List<StudentWithMark> studentWithMarks = new ArrayList<>();
            for (User user : allStudentsOfRealizedCourse) {
                DtoMark dtoMark = new DtoMark(studentMarkDao.getStudentMarkForStudentAndRealizedCourse(user.getId(), realizedCourseId));
                studentWithMarks.add(new StudentWithMark(user, dtoMark));
            }
            writeToResponseBody(studentWithMarks, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("cant get all students");
        }
    }

    @Url("/all-trainers")
    public void allTrainers(HttpServletRequest request, HttpServletResponse response) {
        try (UserDao userDao = abstractDaoFactory.getUserDao()) {
            writeToResponseBody(userDao.getAllOfRole(Role.TRAINER), response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("cant get all trainers");
        }
    }

    @Url("/get-all-trainers-of-student")
    public void allTrainersOfStudent(HttpServletRequest request, HttpServletResponse response) {
        try (UserDao userDao = abstractDaoFactory.getUserDao()) {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            writeToResponseBody(userDao.getAllLecturersForStudent(studentId), response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("cant get-all-trainers-of-student");
        }
    }

    @Url("/get-by-id")
    public void byId(HttpServletRequest request, HttpServletResponse response) {
        try (UserDao userDao = abstractDaoFactory.getUserDao()) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            writeToResponseBody(userDao.getEntityById(userId), response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("cant get-by-id");
        }
    }


}
